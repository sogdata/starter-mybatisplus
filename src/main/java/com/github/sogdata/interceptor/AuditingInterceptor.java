package com.github.sogdata.interceptor;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import com.github.sogdata.annotation.CreatedBy;
import com.github.sogdata.annotation.CreatedDate;
import com.github.sogdata.annotation.LastModifiedBy;
import com.github.sogdata.annotation.LastModifiedDate;
import com.github.sogdata.enumeration.Creator;
import com.github.sogdata.model.AccountAttribute;

/**
 * Auditing interceptor
 *
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class AuditingInterceptor implements Interceptor {

	@Autowired
	private AccountAttribute accountAttribute;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];

		if (null == parameter)
			return invocation.proceed();

		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

		Class<? extends Object> parameterClass = parameter.getClass();

		Field[] fields = parameterClass.getDeclaredFields();

		Field lastModifiedDateField = null;
		Field createdDateField = null;

		Field lastModifiedByField = null;
		Field createdByField = null;

		for (Field x : fields) {
			if (x.isAnnotationPresent(CreatedDate.class)) {
				createdDateField = x;
				continue;
			}
			if (x.isAnnotationPresent(CreatedBy.class)) {
				createdByField = x;
				continue;
			}
			if (x.isAnnotationPresent(LastModifiedDate.class)) {
				lastModifiedDateField = x;
				continue;
			}
			if (x.isAnnotationPresent(LastModifiedBy.class)) {
				lastModifiedByField = x;
				continue;
			}
		}

		if (SqlCommandType.INSERT.equals(sqlCommandType)) {
			this.setFieldValue(lastModifiedDateField, parameter, LastModifiedDate.class);
			this.setFieldValue(createdDateField, parameter, CreatedDate.class);
			this.setFieldValue(lastModifiedByField, parameter, LastModifiedBy.class);
			this.setFieldValue(createdByField, parameter, CreatedBy.class);
		}

		if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
			if (!ParamMap.class.equals(parameterClass)) {
				this.setFieldValue(lastModifiedDateField, parameter, LastModifiedDate.class);
				this.setFieldValue(lastModifiedByField, parameter, LastModifiedBy.class);
			}
			// mybatis plus
			else {
				ParamMap<?> map = (ParamMap<?>) parameter;
				Entry<String, ?> entity = map.entrySet().iterator().next();
				Object o = entity.getValue();
				fields = o.getClass().getDeclaredFields();

				for (Field x : fields) {
					if (x.isAnnotationPresent(LastModifiedDate.class)) {
						lastModifiedDateField = x;
						continue;
					}
					if (x.isAnnotationPresent(LastModifiedBy.class)) {
						lastModifiedByField = x;
						continue;
					}
				}

				this.setFieldValue(lastModifiedDateField, o, LastModifiedDate.class);
				this.setFieldValue(lastModifiedByField, o, LastModifiedBy.class);

			}
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return target instanceof Executor ? Plugin.wrap(target, this) : target;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
	}

	private <T> void setFieldValue(Field field, Object target, Class<T> clazz) {
		if (null == field)
			return;

		Class<?> type = field.getType();

		field.setAccessible(true);

		if (clazz.equals(CreatedDate.class) || clazz.equals(LastModifiedDate.class)) {
			if (type.equals(LocalDateTime.class))
				ReflectionUtils.setField(field, target, LocalDateTime.now());
			else if (type.equals(LocalDate.class))
				ReflectionUtils.setField(field, target, LocalDate.now());
			else if (type.equals(Timestamp.class))
				ReflectionUtils.setField(field, target, Timestamp.from(Instant.now()));
			else if (type.equals(java.util.Date.class))
				ReflectionUtils.setField(field, target, Timestamp.from(Instant.now()));
			else if (type.equals(java.sql.Date.class))
				ReflectionUtils.setField(field, target, java.sql.Date.from(Instant.now()));
			else
				;
		} else if (null != accountAttribute.getAttributes()) {
			if (clazz.equals(CreatedBy.class)) {
				CreatedBy createdByAnnotation = field.getAnnotation(CreatedBy.class);
				Creator creator = (Creator) AnnotationUtils.getValue(createdByAnnotation);
				ReflectionUtils.setField(field, target, accountAttribute.getAttributes().getOrDefault(creator, null));
			}
			if (clazz.equals(LastModifiedBy.class)) {
				LastModifiedBy lastModifiedByAnnotation = field.getAnnotation(LastModifiedBy.class);
				Creator creator = (Creator) AnnotationUtils.getValue(lastModifiedByAnnotation);
				ReflectionUtils.setField(field, target, accountAttribute.getAttributes().getOrDefault(creator, null));
			}
		}
		field.setAccessible(false);
	}

}