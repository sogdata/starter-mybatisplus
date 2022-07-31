package com.github.sogdata.model;

/**
 * Page attribute model
 *
 */
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

	/**
	 * Builder
	 *
	 */
	public static final class Builder {

		private PageAttribute attribute;

		/**
		 * Builder
		 */
		public Builder() {
			attribute = new PageAttribute();
		}

		/**
		 * build page attribute
		 * 
		 * @return page attribute
		 */
		public PageAttribute build() {
			return attribute;
		}

		/**
		 * set offset start page
		 * 
		 * @param value offset
		 * @return Builder
		 */
		public Builder offset(Integer value) {
			attribute.setOffset(value);
			return this;
		}

		/**
		 * set current page
		 * 
		 * @param value page
		 * @return Builder
		 */
		public Builder page(String value) {
			attribute.setPage(value);
			return this;
		}

		/**
		 * set page size
		 * 
		 * @param value size
		 * @return Builder
		 */
		public Builder size(String value) {
			attribute.setSize(value);
			return this;
		}

		/**
		 * set page sort
		 * 
		 * @param value sort
		 * @return Builder
		 */
		public Builder sort(String value) {
			attribute.setSort(value);
			return this;
		}

		/**
		 * set content
		 * 
		 * @param value content
		 * @return Builder
		 */
		public Builder content(String value) {
			attribute.setContent(value);
			return this;
		}

		/**
		 * set total pages
		 * 
		 * @param value total pages
		 * @return Builder
		 */
		public Builder totalPages(String value) {
			attribute.setTotalPages(value);
			return this;
		}

		/**
		 * set total elements
		 * 
		 * @param value total elements
		 * @return Builder
		 */
		public Builder totalElements(String value) {
			attribute.setTotalElements(value);
			return this;
		}

	}

	/**
	 * Getter
	 * 
	 * @return offset start page
	 */
	public Integer getOffset() {
		return offset;
	}

	/**
	 * Setter
	 * 
	 * @param offset offset start page
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * Getter
	 * 
	 * @return currentpage
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Setter
	 * 
	 * @param page current page
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * Getter
	 * 
	 * @return size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Setter
	 * 
	 * @param size size
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * Getter
	 * 
	 * @return srot
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * Setter
	 * 
	 * @param sort sort
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * Getter
	 * 
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Setter
	 * 
	 * @param content content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Getter
	 * 
	 * @return total pages
	 */
	public String getTotalPages() {
		return totalPages;
	}

	/**
	 * Setter
	 * 
	 * @param totalPages total pages
	 */
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * Getter
	 * 
	 * @return total elements
	 */
	public String getTotalElements() {
		return totalElements;
	}

	/**
	 * Setter
	 * 
	 * @param totalElements total elements
	 */
	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}

}
