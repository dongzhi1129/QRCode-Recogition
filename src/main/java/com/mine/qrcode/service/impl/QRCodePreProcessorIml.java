package com.mine.qrcode.service.impl;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mine.qrcode.common.exception.ServiceException;
import com.mine.qrcode.component.QRCodeRecogitionProperties;
import com.mine.qrcode.service.AbstractBaseQRCodeService;
import com.mine.qrcode.service.QRCodePreProcessor;

@Service
public class QRCodePreProcessorIml extends AbstractBaseQRCodeService implements QRCodePreProcessor {

	private static final Logger logger = LoggerFactory.getLogger(QRCodeDetectionImpl.class);

	@Autowired
	private QRCodeRecogitionProperties qrCodeRecogitionProperties;

	@Override
	public Mat preProcess(Mat sourceImage) throws ServiceException {
		Mat grayImage = new Mat();
		// convert to gray image
		Imgproc.cvtColor(sourceImage, grayImage, Imgproc.COLOR_RGB2GRAY);
		if (logger.isDebugEnabled()) {
			saveImageToDisk(qrCodeRecogitionProperties.getDebugImagePath(), "gray_image.jpg", grayImage);
		}
		//Gaussion blur and threshold
		Mat gaussianBlurImage =  new Mat(),thresholdImage =  new Mat();
		Imgproc.GaussianBlur(grayImage, gaussianBlurImage, new Size(3,3), 0);
		Imgproc.threshold(gaussianBlurImage, thresholdImage, 50, 255, Imgproc.THRESH_BINARY|Imgproc.THRESH_OTSU);
		if (logger.isDebugEnabled()) {
			saveImageToDisk(qrCodeRecogitionProperties.getDebugImagePath(), "blur_and_threshold_image.jpg", thresholdImage);
		}
		// morphology
		Mat morphologyImage =  new Mat();
		Mat blur = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(new double[]{5.0,5.0}));
		Imgproc.morphologyEx(thresholdImage, morphologyImage, Imgproc.MORPH_OPEN, blur,new Point(-1,-1),5);
		if (logger.isDebugEnabled()) {
			saveImageToDisk(qrCodeRecogitionProperties.getDebugImagePath(), "morphology_image.jpg", morphologyImage);
		}
		return morphologyImage;
	}

}
