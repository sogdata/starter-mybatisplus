package com.github.sogdata.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.sogdata.enumeration.Direction;

/**
 * Default pageable attribute
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PageableDefault {

	/**
	 * Alias for size. Prefer to use the size method as it
	 * makes the annotation declaration more expressive and you'll probably want to
	 * configure the page anyway.
	 *
	 * @return value
	 */
	int value() default 10;

	/**
	 * The default-size the injected
	 * pageable should get if no
	 * corresponding parameter defined in request (default is 10).
	 * 
	 * @return size
	 */
	int size() default 10;

	/**
	 * The default-pagenumber the injected
	 * pageable should get if no
	 * corresponding parameter defined in request (default is 0).
	 * 
	 * @return page
	 */
	int page() default 0;

	/**
	 * The properties to sort by by default. If unset, no sorting will be applied at
	 * all.
	 *
	 * @return sorts
	 */
	String[] sort() default {};

	/**
	 * The direction to sort by. Defaults to asc.
	 *
	 * @return Direction
	 */
	Direction direction() default Direction.ASC;
}
