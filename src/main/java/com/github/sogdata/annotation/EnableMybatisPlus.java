package com.github.sogdata.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.github.sogdata.interceptor.PageImportSelector;

/**
 * Enable mybatis plus configuration
 *
 */
@Import(PageImportSelector.class)
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EnableMybatisPlus {

	/**
	 * default true
	 * @return boolean value
	 */
	boolean value() default true;

}
