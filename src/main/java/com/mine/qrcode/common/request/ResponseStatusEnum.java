package com.mine.qrcode.common.request;

import org.springframework.http.HttpStatus;

public enum ResponseStatusEnum {

	REPONSE_STATUS_SUCCESS(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()),

	REPONSE_STATUS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

	private Integer status;

	private String description;

	private ResponseStatusEnum(Integer status, String description) {
		this.status = status;
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

}
