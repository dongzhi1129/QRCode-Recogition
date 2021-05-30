package com.mine.qrcode.service;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

public interface QRCodeDetection {

	List<MatOfPoint> listPossibleQrCodePositions(Mat thresholdImage);

	List<MatOfPoint> prefilterImpossibleQrCode(List<MatOfPoint> possibleQrCodePositions);

	List<MatOfPoint> filetrQrCodeByQRCodeStandar(Mat srcImage, List<MatOfPoint> prefilterQrCodePositons);

}
