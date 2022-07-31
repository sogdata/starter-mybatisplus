package com.github.sogdata.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;

/**
 * Table utils
 *
 */
public class TableUtils {

	/**
	 * Get mapped colum properties
	 * @param clazz class
	 * @return mapped colum properties
	 */
	public static Map<String, String> getMappedColumProperties(Class<?> clazz) {
		TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
		return getMappedColumProperties(tableInfo);
	}

	/**
	 * Get mapped colum properties
	 * @param tableInfo table info
	 * @return mapped colum properties
	 */
	public static Map<String, String> getMappedColumProperties(TableInfo tableInfo) {
		Map<String, String> map = new HashMap<String, String>();
		String keyProperty = tableInfo.getKeyProperty();
		if (StringUtils.hasText(keyProperty))
			map.put(keyProperty.toUpperCase(), tableInfo.getKeyColumn());
		tableInfo.getFieldList().forEach(x -> map.put(x.getProperty().toUpperCase(), x.getColumn()));
		return map;
	}

}