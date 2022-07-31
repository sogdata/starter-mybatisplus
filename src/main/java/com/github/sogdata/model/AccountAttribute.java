package com.github.sogdata.model;

import java.util.Map;

import com.github.sogdata.enumeration.Creator;

public class AccountAttribute {

	private Map<Creator, Object> attributes;

	public static final class Builder {

		private AccountAttribute attribute;

		public Builder() {
			attribute = new AccountAttribute();
		}

		public AccountAttribute build() {
			return attribute;
		}

		public Builder attributes(Map<Creator, Object> value) {
			attribute.setAttributes(value);
			return this;
		}
	}

	public Map<Creator, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<Creator, Object> attributes) {
		this.attributes = attributes;
	}
}
