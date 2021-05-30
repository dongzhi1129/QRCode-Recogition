package com.mine.qrcode.service.impl;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.qrcode.common.exception.ServiceException;
import com.mine.qrcode.service.AbstractBaseQRCodeService;
import com.mine.qrcode.service.QRCodePreProcessor;
import com.mine.qrcode.service.QRCodeRecogition;

@Service
public class QRCodeRecogitionImpl extends AbstractBaseQRCodeService implements QRCodeRecogition {
	
	@Autowired
	private QRCodePreProcessor qrCodePreProcessor;

	@Override
	public void recognize(String sourceImage ) throws ServiceException {
		String imageAbosolutePath = getImageAbosolutePath(sourceImage);
		Mat srcImage;
		try {
			srcImage = Imgcodecs.imread(imageAbosolutePath);
			if(srcImage.empty()) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new ServiceException(String.format("Image [%s] not found. ", sourceImage));
		}
		qrCodePreProcessor.preProcess(srcImage);
	}

}
