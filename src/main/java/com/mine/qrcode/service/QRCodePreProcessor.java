package com.mine.qrcode.service;

import org.opencv.core.Mat;

public interface QRCodePreProcessor {

	Mat preProcess(Mat sourceImage);

}
