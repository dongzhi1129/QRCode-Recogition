package com.mine.qrcode.service;

import java.util.List;

import org.opencv.core.Mat;

public interface QRCodeDetection {

	List<Mat> listPossibleQrCodePositions(Mat srcImage,Mat thresholdImage);

	List<Mat> prefilterImpossibleQrCode(List<Mat> possibleQrCodes);
}
