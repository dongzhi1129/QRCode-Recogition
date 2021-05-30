package com.mine.qrcode.service.impl;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mine.qrcode.service.QRCodeDetection;

@Service
public class QRCodeDetectionImpl implements QRCodeDetection {

	private static final Logger logger = LoggerFactory.getLogger(QRCodeDetectionImpl.class);

	@Override
	public List<MatOfPoint> listPossibleQrCodePositions(Mat thresholdImage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MatOfPoint> prefilterImpossibleQrCode(List<MatOfPoint> possibleQrCodePositions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MatOfPoint> filetrQrCodeByQRCodeStandar(Mat srcImage, List<MatOfPoint> prefilterQrCodePositons) {
		// TODO Auto-generated method stub
		return null;
	}

}
