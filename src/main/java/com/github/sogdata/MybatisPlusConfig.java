package com.github.sogdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.sogdata.interceptor.AuditingInterceptor;
import com.github.sogdata.interceptor.PageHandlerInterceptor;
import com.github.sogdata.model.AccountAttribute;
import com.github.sogdata.model.PageAttribute;
import com.github.sogdata.model.PageCombinedSerializer;

/**
 * Mybatis plus configuration 
 *
 */
@Configuration
public class MybatisPlusConfig {

	/**
	 * Page attribute bean
	 * @return page attibute
	 */
	@Bean
	@ConditionalOnMissingBean
	PageAttribute pageAttribute() {
		return new PageAttribute();
	}

	/**
	 * Account attribute bean
	 * @return account attribute
	 */
	@Bean
	@ConditionalOnMissingBean
	AccountAttribute accountAttribute() {
		return new AccountAttribute();
	}

	/**
	 * Auditing interceptor bean
	 * @return auditing interceptor
	 */
	@Bean
	@Primary
	@ConditionalOnBean(value = PageAttribute.class)
	@ConditionalOnMissingBean
	AuditingInterceptor auditingInterceptor() {
		return new AuditingInterceptor();
	}

	/**
	 * PageCombined serializer bean
	 * @param <T>
	 * @return	pageCombined serializer
	 */
	@Bean
	@ConditionalOnBean(value = PageAttribute.class)
	@ConditionalOnMissingBean
	<T> PageCombinedSerializer<T> PageCombinedSerializer() {
		return new PageCombinedSerializer<T>();
	}

	@Autowired
	private ApplicationContext context;

	/**
	 * Add page interceptor
	 * @return WebMvcConfigurer
	 */
	@Bean
	@ConditionalOnBean(value = PageAttribute.class)
	WebMvcConfigurer paginationRequestDefault() {
		return new WebMvcConfigurer() {
			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
				WebMvcConfigurer.super.addArgumentResolvers(resolvers);
				resolvers.add(new PageHandlerInterceptor<>(context));
			}
		};
	}

}