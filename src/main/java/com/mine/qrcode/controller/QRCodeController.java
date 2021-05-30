package com.mine.qrcode.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
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
	public ApiResult<?> recoginizeQrCode(@ApiParam(defaultValue = "20210527190334.jpg") @RequestParam String imageName) {
		String qrContent = qrCodeRecogition.recognize(imageName);
		return ApiResult.success().setRows(qrContent);
	}
	
	public static void main(String[] args) throws ChecksumException, FormatException {
		QRCodeReader multiFormatReader = new QRCodeReader();
		File file = new File("E:\\Project\\TEST\\test\\qrcode\\possible_QR_image1.jpg");
		if(file.exists()) {
			System.out.println("ok");
		} else {
			System.out.println("not ok");
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

		// 定义二维码的参数
		HashMap hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);        //定义纠错等级
		hints.put(DecodeHintType.TRY_HARDER, null);    //边框空白
		try {
			Result result = multiFormatReader.decode(binaryBitmap);
			System.out.println("解析结果：" + result.toString());
			System.out.println("二维码类型：" + result.getBarcodeFormat());
			System.out.println("二维码文本内容：" + result.getText());
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
