package com.mine.qrcode.service;

import com.mine.qrcode.common.exception.ServiceException;

public interface QRCodeRecogition {
	
	String recognize(String sourceImage) throws ServiceException;

}
