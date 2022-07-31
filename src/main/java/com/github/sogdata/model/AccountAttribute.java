package com.github.sogdata.model;

import java.util.Map;

import com.github.sogdata.enumeration.Creator;

/**
 * Account attribute model
 *
 */
public class AccountAttribute {

	private Map<Creator, Object> attributes;

	/**
	 * Builder
	 *
	 */
	public static final class Builder {

		private AccountAttribute attribute;

		/**
		 * Builder
		 */
		public Builder() {
			attribute = new AccountAttribute();
		}

		/**
		 * build account attribute
		 * 
		 * @return account attribute
		 */
		public AccountAttribute build() {
			return attribute;
		}

		/**
		 * set account attributes
		 * 
		 * @param value account attributes
		 * @return Builder
		 */
		public Builder attributes(Map<Creator, Object> value) {
			attribute.setAttributes(value);
			return this;
		}
	}

	/**
	 * Getter
	 * 
	 * @return account attributes
	 */
	public Map<Creator, Object> getAttributes() {
		return attributes;
	}

	/**
	 * Setter
	 * 
	 * @param attributes account attributes
	 */
	public void setAttributes(Map<Creator, Object> attributes) {
		this.attributes = attributes;
	}
}
