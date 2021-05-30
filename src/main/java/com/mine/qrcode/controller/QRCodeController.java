package com.mine.qrcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mine.qrcode.common.request.ApiResult;
import com.mine.qrcode.service.QRCodeRecogition;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Api
@RestController
@RequestMapping("/v1/qrcode")
@Validated
public class QRCodeController {
	
	@Autowired
	private QRCodeRecogition qrCodeRecogition;
	
	@GetMapping("/recogition")
	@ApiOperation(value="recognize the qrcode",httpMethod = "GET")
	@ApiResponse(code = 200, message = "call successfully")
	public ApiResult<?> recoginizeQrCode(@ApiParam @RequestParam String imageName) {
		qrCodeRecogition.recognize(imageName);
		return ApiResult.success();
	}
}
