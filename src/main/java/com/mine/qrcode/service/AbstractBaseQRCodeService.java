package com.mine.qrcode.service;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.mine.qrcode.common.constant.QRCodeConstants;
import com.mine.qrcode.common.exception.ServiceException;
import com.mine.qrcode.component.QRCodeRecogitionProperties;

public abstract class AbstractBaseQRCodeService {

	@Autowired
	protected QRCodeRecogitionProperties qrCodeRecogitionProperties;

	protected Boolean saveImageToDisk(String relativePath, String imageName, Mat toBeSavedImage) {
		String abosolutePathPrefix = qrCodeRecogitionProperties.getPrefix();
		String imagePath = String.format(QRCodeConstants.DEFAULT_PATH_FORMAT, abosolutePathPrefix, relativePath,
				imageName);
		boolean isSavedSuccess = Imgcodecs.imwrite(imagePath, toBeSavedImage);
		return isSavedSuccess;
	}

	protected String getImageAbosolutePath(String imageName) {
		String abosolutePathPrefix = qrCodeRecogitionProperties.getPrefix();
		String imagePath = String.format(QRCodeConstants.DEFAULT_PATH_FORMAT, abosolutePathPrefix,
				qrCodeRecogitionProperties.getSourceImagePath(), imageName);
		return imagePath;
	}

	protected String getDebugFolderPath() {
		return qrCodeRecogitionProperties.getDebugImagePath();
	}

	protected String getQRCodeFolderPath() {
		return qrCodeRecogitionProperties.getAchievedQrcodePath();
	}
	
	protected String getImageType(String imageName) throws ServiceException {
		try {
			if (StringUtils.isEmpty(imageName)) {
				throw new Exception();
			}
			int lastIndex = imageName.lastIndexOf(".");
			if (lastIndex == -1) {
				throw new Exception();
			}
			return imageName.substring(lastIndex);
		} catch (Exception e) {
			throw new ServiceException("illegal image type.");
		}

	}

}
