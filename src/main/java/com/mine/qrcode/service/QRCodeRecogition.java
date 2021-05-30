package com.mine.qrcode.service;

import com.mine.qrcode.common.exception.ServiceException;

public interface QRCodeRecogition {
	
	void recognize(String sourceImage) throws ServiceException;

}
