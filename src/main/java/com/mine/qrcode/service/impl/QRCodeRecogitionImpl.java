package com.mine.qrcode.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.collections4.CollectionUtils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.mine.qrcode.common.exception.ServiceException;
import com.mine.qrcode.service.AbstractBaseQRCodeService;
import com.mine.qrcode.service.QRCodeDetection;
import com.mine.qrcode.service.QRCodePreProcessor;
import com.mine.qrcode.service.QRCodeRecogition;

@Service
public class QRCodeRecogitionImpl extends AbstractBaseQRCodeService implements QRCodeRecogition {

	private static final Logger logger = LoggerFactory.getLogger(QRCodeDetectionImpl.class);

	@Autowired
	private QRCodePreProcessor qrCodePreProcessor;

	@Autowired
	private QRCodeDetection qrCodeDetection;

	@Override
	public String recognize(String sourceImage) throws ServiceException {
		String imageAbosolutePath = getImageAbosolutePath(sourceImage);
		Mat srcImage;
		try {
			srcImage = Imgcodecs.imread(imageAbosolutePath);
			if (srcImage.empty()) {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new ServiceException(String.format("Image [%s] not found. ", sourceImage));
		}
		Mat proProcessImage = qrCodePreProcessor.preProcess(srcImage);
		List<Mat> possibleQrCodes= qrCodeDetection.listPossibleQrCodePositions(srcImage, proProcessImage);
		if(CollectionUtils.isEmpty(possibleQrCodes)) {
			throw new ServiceException("No found QR Code.");
		}
		List<Mat> prefilterQrCodes = qrCodeDetection.prefilterImpossibleQrCode(possibleQrCodes);
		if(CollectionUtils.isEmpty(prefilterQrCodes)) {
			throw new ServiceException("No found QR Code.");
		}
		String imageType = getImageType(sourceImage);
		String qrCodeContent = recognizeQRCode(prefilterQrCodes.get(0),imageType);
		return qrCodeContent;
	}

	private String recognizeQRCode(Mat image, String fileType) {
		// openCV mat to bufferedImage
		MatOfByte mob = new MatOfByte();
		Imgcodecs.imencode(fileType, image, mob);
		byte[] byteArray = mob.toArray();
		BufferedImage bufImage = null;
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
		} catch (Exception e) {
			throw new ServiceException("Mat convert to Buffered image failed.");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new ServiceException("Mat convert to Buffered image failed.");
				}
			}

		}
		// parse qr code
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufImage)));
		HashMap hints = Maps.newHashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(DecodeHintType.TRY_HARDER, null);
		MultiFormatReader multiFormatReader = new MultiFormatReader();
		try {
			Result result = multiFormatReader.decode(binaryBitmap, hints);
			return result.getText();
		} catch (NotFoundException e) {
			logger.error("",e);
			throw new ServiceException("Decode QR Code failed.");
		}
	}
}
