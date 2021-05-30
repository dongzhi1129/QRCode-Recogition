package com.mine.qrcode.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mine.qrcode.service.AbstractBaseQRCodeService;
import com.mine.qrcode.service.QRCodeDetection;

@Service
public class QRCodeDetectionImpl extends AbstractBaseQRCodeService implements QRCodeDetection {

	private static final Logger logger = LoggerFactory.getLogger(QRCodeDetectionImpl.class);

	@Override
	public List<Mat> listPossibleQrCodePositions(Mat srcImage, Mat thresholdImage) {
		// candy
		Mat candyImage = new Mat();
		Imgproc.Canny(thresholdImage, candyImage, 112, 255);
		if (logger.isDebugEnabled()) {
			saveImageToDisk(getDebugFolderPath(), "morphology_image.jpg", candyImage);
		}
		// find all of the contours
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(candyImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Mat copyiedSrcImage = srcImage.clone();
		List<Mat> possibleQRCodeAreas = Lists.newArrayList();
		for (int ite = 0; ite < contours.size(); ite++) {
			if (logger.isDebugEnabled()) {
				// rectangel the contours
				Imgproc.drawContours(copyiedSrcImage, contours, ite, new Scalar(0, 255, 0), -1);
			}
			// pre filter the qrcode
			MatOfPoint currentContour = contours.get(ite);
			MatOfPoint2f currentContourTemp = new MatOfPoint2f(currentContour.toArray());
			RotatedRect roi = Imgproc.minAreaRect(currentContourTemp);
			// filter roi
			
			// rotated roi
			Rect boundingRect = Imgproc.boundingRect(currentContour);
			Mat boundingRectRoi = new Mat(srcImage, boundingRect);
//			if(logger.isDebugEnabled()) {
//				saveImageToDisk(getDebugFolderPath(), "possible_QR_image" + ite + ".jpg", boundingRectRoi);
//			}
			Mat rotatedPossibleArea = rotatedRoi(boundingRectRoi, roi);
			if(logger.isDebugEnabled()) {
				saveImageToDisk(getDebugFolderPath(), "possible_QR_image" + ite + ".jpg", rotatedPossibleArea);
			}
			possibleQRCodeAreas.add(rotatedPossibleArea);
		}
		// mark the possible qrcode
		if (logger.isDebugEnabled()) {
			saveImageToDisk(getDebugFolderPath(), "contours_image.jpg", copyiedSrcImage);
		}
		return possibleQRCodeAreas;
	}

	@Override
	public List<Mat> prefilterImpossibleQrCode(List<Mat> possibleQrCodes) {
		if(CollectionUtils.isEmpty(possibleQrCodes)) {
			return Lists.newArrayList();
		}
		List<Mat> prefilterQRCodes = Lists.newArrayList();
		//pre filter QRCode
		QRCodeDetector qrCodeDetector = new QRCodeDetector();
		for (Mat qrcode : possibleQrCodes) {
			boolean isDectected = qrCodeDetector.detectMulti(qrcode, new Mat());
			if(isDectected) {
				if (logger.isDebugEnabled()) {
					saveImageToDisk(getQRCodeFolderPath(), "possible_QR_image.jpg", qrcode);
				}
				prefilterQRCodes.add(qrcode);
				String parseContent = qrCodeDetector.detectAndDecode(qrcode);
				logger.info("------------------------------------->" + parseContent);
			}
		}
		if(!prefilterQRCodes.isEmpty()) {
			return prefilterQRCodes;
		}
		return prefilterQRCodes;
	}

	private Mat rotatedRoi(Mat srcImage, RotatedRect possibleArea) {
		double possibleAreaWidth = possibleArea.size.width;
		double possibleAreaHeight = possibleArea.size.height;
		double angle = possibleArea.angle,roatedAngle = possibleArea.angle;
		double rate = possibleAreaWidth / possibleAreaHeight;
		if(rate < 1) {
			roatedAngle = 90 + angle;
		}
		Mat rotationMatrix2D = Imgproc.getRotationMatrix2D(possibleArea.center, roatedAngle, 1);
		Mat dstImage = new Mat();
		Imgproc.warpAffine(srcImage, dstImage, rotationMatrix2D, srcImage.size(), Imgproc.INTER_CUBIC);
		return dstImage;
	}

}
