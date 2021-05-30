package com.mine.qrcode.service;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;

import com.mine.qrcode.common.constant.QRCodeConstants;
import com.mine.qrcode.component.QRCodeRecogitionProperties;

public abstract class AbstractBaseQRCodeService {
	
	@Autowired
	protected QRCodeRecogitionProperties qrCodeRecogitionProperties;
	
	protected Boolean saveImageToDisk(String relativePath, String imageName, Mat toBeSavedImage) {
		String abosolutePathPrefix = qrCodeRecogitionProperties.getPrefix();
		String imagePath = String.format(QRCodeConstants.DEFAULT_PATH_FORMAT, abosolutePathPrefix,relativePath,imageName);
		boolean isSavedSuccess = Imgcodecs.imwrite(imagePath, toBeSavedImage);
		return isSavedSuccess;
	}
	
	protected String getImageAbosolutePath(String imageName) {
		String abosolutePathPrefix = qrCodeRecogitionProperties.getPrefix();
		String imagePath = String.format(QRCodeConstants.DEFAULT_PATH_FORMAT, abosolutePathPrefix,qrCodeRecogitionProperties.getSourceImagePath(),imageName);
		return imagePath;		
	}


}
