package com.github.sogdata.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.sogdata.enumeration.Creator;

/**
 * Created by
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { FIELD, METHOD, ANNOTATION_TYPE })
public @interface CreatedBy {
	
	/**
	 * Creator attribute
	 * @return Creator enumeration
	 */
	Creator value() default Creator.ID;
}