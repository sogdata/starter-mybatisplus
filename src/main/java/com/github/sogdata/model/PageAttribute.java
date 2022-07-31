package com.github.sogdata.model;

public class PageAttribute {

	/*
	 * offset the first page number
	 */
	private Integer offset = 0;

	/*
	 * alias for page
	 */
	private String page = "page";

	/*
	 * alias for size
	 */
	private String size = "size";

	/*
	 * alias for sort
	 */
	private String sort = "sort";

	/*
	 * alias for content
	 */
	private String content = "content";

	/*
	 * alias for totalPages
	 */
	private String totalPages = "totalPages";

	/*
	 * alias for totalElements
	 */
	private String totalElements = "totalElements";

	public static final class Builder {

		private PageAttribute attribute;

		public Builder() {
			attribute = new PageAttribute();
		}

		public PageAttribute build() {
			return attribute;
		}

		public Builder offset(Integer value) {
			attribute.setOffset(value);
			return this;
		}

		public Builder page(String value) {
			attribute.setPage(value);
			return this;
		}

		public Builder size(String value) {
			attribute.setSize(value);
			return this;
		}

		public Builder sort(String value) {
			attribute.setSort(value);
			return this;
		}

		public Builder content(String value) {
			attribute.setContent(value);
			return this;
		}

		public Builder totalPages(String value) {
			attribute.setTotalPages(value);
			return this;
		}

		public Builder totalElements(String value) {
			attribute.setTotalElements(value);
			return this;
		}

	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public String getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}

}
