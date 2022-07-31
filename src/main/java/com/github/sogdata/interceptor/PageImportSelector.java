package com.github.sogdata.interceptor;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.github.sogdata.MybatisPlusConfig;
import com.github.sogdata.annotation.EnableMybatisPlus;

public class PageImportSelector implements ImportSelector {

	/**
	 * Select and return the names of which class(es) should be imported based on
	 * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
	 *
	 * @param importingClassMetadata
	 * @return the class names, or an empty array if none
	 */
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(EnableMybatisPlus.class.getName(), false));
		boolean value = attributes.getBoolean("value");
		return value ? new String[] { MybatisPlusConfig.class.getName() } : new String[0];
	}

}
