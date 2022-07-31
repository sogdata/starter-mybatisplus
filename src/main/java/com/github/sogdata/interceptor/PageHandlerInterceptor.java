package com.github.sogdata.interceptor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.sogdata.annotation.PageableDefault;
import com.github.sogdata.enumeration.Direction;
import com.github.sogdata.model.PageAttribute;
import com.github.sogdata.util.TableUtils;

public class PageHandlerInterceptor<T> implements HandlerMethodArgumentResolver {

	private PageAttribute pageAttribute;

	private ApplicationContext context;

	public PageHandlerInterceptor() {
		this.pageAttribute = new PageAttribute();
	}

	public PageHandlerInterceptor(ApplicationContext context) {
		this.context = context;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(Page.class)
				&& parameter.hasParameterAnnotation(PageableDefault.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		if (null == pageAttribute) {
			pageAttribute = context.getBean(PageAttribute.class);
		}

		String page = webRequest.getParameter(pageAttribute.getPage());
		String size = webRequest.getParameter(pageAttribute.getSize());

		String[] sorts = webRequest.getParameterValues(pageAttribute.getSort());

		PageableDefault pageable = parameter.getParameterAnnotation(PageableDefault.class);

		Page<T> model = new Page<T>();

		model.setCurrent(
				pageAttribute.getOffset() + (StringUtils.hasText(page) ? Long.valueOf(page) : pageable.page()));
		model.setSize(StringUtils.hasText(size) ? Long.valueOf(size) : pageable.size());

		if (null == sorts || sorts.length < 1)
			sorts = pageable.sort();

		if (null == sorts || sorts.length < 1)
			return model;

		List<String> descs = new ArrayList<String>();
		List<String> ascs = new ArrayList<String>();

		for (String x : sorts) {
			x = x.trim();
			if (!(x.toUpperCase().endsWith("," + Direction.DESC.name())
					|| x.toUpperCase().endsWith("," + Direction.ASC.name())))
				x += ",".concat(Direction.ASC.name());
			String[] computedString = StringUtils.commaDelimitedListToStringArray(x.trim());
			for (int y = 0; y < computedString.length; ++y) {
				if (0 == y)
					continue;
				String currentString = computedString[y];
				if (Direction.DESC.name().equalsIgnoreCase(currentString))
					descs.add(computedString[y - 1]);
				if (Direction.ASC.name().equalsIgnoreCase(currentString))
					ascs.add(computedString[y - 1]);
			}
		}

		boolean hasDescs = !descs.isEmpty();
		boolean hasAscs = !ascs.isEmpty();

		if (!hasDescs && !hasAscs)
			return model;

		if (hasDescs)
			model.addOrder(OrderItem.descs(descs.toArray(new String[descs.size()])));

		if (hasAscs)
			model.addOrder(OrderItem.ascs(ascs.toArray(new String[ascs.size()])));

		ResolvableType[] resolvableTypes = ResolvableType.forMethodParameter(parameter).getGenerics();

		if (null == resolvableTypes || resolvableTypes.length < 1)
			return model;

		Class<?> clazz = resolvableTypes[0].resolve();

		if (null == clazz)
			return model;

		Map<String, String> columnPropertyMap = this.getColumnPropertyMap(clazz);

		if (null == columnPropertyMap || columnPropertyMap.isEmpty())
			return model;

		if (hasDescs) {
			this.removeOrder(model.orders(), item -> !item.isAsc());
			model.addOrder(descs.stream().map(x -> {
				return OrderItem.desc(!StringUtils.hasText(columnPropertyMap.get(x)) ? x : columnPropertyMap.get(x));
			}).collect(Collectors.toList()));
		}

		if (hasAscs) {
			this.removeOrder(model.orders(), OrderItem::isAsc);
			model.addOrder(ascs.stream().map(x -> {
				return OrderItem.asc(!StringUtils.hasText(columnPropertyMap.get(x)) ? x : columnPropertyMap.get(x));
			}).collect(Collectors.toList()));
		}

		return model;
	}

	private Map<String, String> getColumnPropertyMap(Class<?> clazz) {
		Map<String, String> result = new HashMap<>();

		TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
		boolean hasTableInfo = null != tableInfo;
		boolean isUnderCamel = false;
		if (hasTableInfo)
			isUnderCamel = tableInfo.isUnderCamel();
		else {
			// TODO:
			List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
			isUnderCamel = CollectionUtils.isEmpty(tableInfos) ? isUnderCamel : tableInfos.get(0).isUnderCamel();
		}

		Map<String, String> columnMap = hasTableInfo ? TableUtils.getMappedColumProperties(tableInfo) : null;

		Field[] fields = clazz.getDeclaredFields();
		for (Field x : fields) {
			String columnName = x.getName();

			if (!hasTableInfo) {
				if (isUnderCamel)
					result.put(columnName,
							com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(columnName));
			} else {

				String columnMappedName = columnMap.get(columnName.toUpperCase());

				if (!StringUtils.hasText(columnMappedName)) {
					TableField tableFieldAnnotation = x.getAnnotation(TableField.class);
					if (null != tableFieldAnnotation && StringUtils.hasText(tableFieldAnnotation.value()))
						columnMappedName = tableFieldAnnotation.value();
					else if (isUnderCamel)
						columnMappedName = com.baomidou.mybatisplus.core.toolkit.StringUtils
								.camelToUnderline(columnName);
				}

				if (StringUtils.hasText(columnMappedName))
					result.put(columnName, columnMappedName);
			}

			// handle JsonProperty annotation column
			JsonProperty jsonAnnotation = x.getAnnotation(JsonProperty.class);

			if (null != jsonAnnotation) {
				if (StringUtils.hasText(jsonAnnotation.defaultValue()))
					result.put(jsonAnnotation.defaultValue(), columnName);

				if (StringUtils.hasText(jsonAnnotation.value()))
					result.put(jsonAnnotation.value(), columnName);
			}
		}
		;

		return result;
	}

	private void removeOrder(List<OrderItem> orders, Predicate<OrderItem> filter) {
		for (int i = orders.size() - 1; i >= 0; i--) {
			if (filter.test(orders.get(i))) {
				orders.remove(i);
			}
		}
	}

}