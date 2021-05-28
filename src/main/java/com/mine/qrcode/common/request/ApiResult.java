package com.mine.qrcode.common.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "result")
public class ApiResult<T> {

	@ApiModelProperty(name = "status", required = true, position = 1)
	private Integer status;

	@ApiModelProperty(name = "message", required = true, position = 2)
	private String message;

	@ApiModelProperty(name = "rows", required = true, position = 3)
	private T rows;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(name = "page", required = false, hidden = true)
	private Page page;

	public ApiResult(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ApiResult(Integer status, String message, T rows) {
		super();
		this.status = status;
		this.message = message;
		this.rows = rows;
	}

	public static <T> ApiResult<T> success() {
		ApiResult<T> apiResult = new ApiResult<T>(ResponseStatusEnum.REPONSE_STATUS_SUCCESS.getStatus(),
				ResponseStatusEnum.REPONSE_STATUS_SUCCESS.getDescription());
		return apiResult;
	}

	public static <T> ApiResult<T> successWithRows(T rows) {
		ApiResult<T> apiResult = success();
		apiResult.setRows(rows);
		return apiResult;
	}

	public static <T> ApiResult<T> failed() {
		ApiResult<T> apiResult = new ApiResult<T>(ResponseStatusEnum.REPONSE_STATUS_ERROR.getStatus(),
				ResponseStatusEnum.REPONSE_STATUS_ERROR.getDescription());
		return apiResult;
	}

	public static <T> ApiResult<T> internalError() {
		ApiResult<T> apiResult = new ApiResult<T>(ResponseStatusEnum.REPONSE_STATUS_ERROR.getStatus(),
				ResponseStatusEnum.REPONSE_STATUS_ERROR.getDescription());
		return apiResult;
	}

	public ApiResult<T> setStatus(Integer status) {
		this.status = status;
		return this;
	}

	public ApiResult<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public ApiResult<T> setRows(T rows) {
		this.rows = rows;
		return this;
	}

	public static class Page {

		private Integer pageSize;

		private Integer pageNumber;

		private Integer totalPage;

		private Integer totalRows;

		public Page(Integer pageSize, Integer pageNumber, Integer totalPage, Integer totalRows) {
			super();
			this.pageSize = pageSize;
			this.pageNumber = pageNumber;
			this.totalPage = totalPage;
			this.totalRows = totalRows;
		}

		public Page(Integer pageSize, Integer pageNumber, Integer totalPage) {
			super();
			this.pageSize = pageSize;
			this.pageNumber = pageNumber;
			this.totalPage = totalPage;
		}

		public Integer getPageSize() {
			return pageSize;
		}

		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}

		public Integer getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
		}

		public Integer getTotalPage() {
			return totalPage;
		}

		public void setTotalPage(Integer totalPage) {
			this.totalPage = totalPage;
		}

		public Integer getTotalRows() {
			return totalRows;
		}

		public void setTotalRows(Integer totalRows) {
			this.totalRows = totalRows;
		}
		
		

	}

}
