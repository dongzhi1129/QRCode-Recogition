package com.mine.qrcode.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mine.qrcode.common.request.ApiResult;

@RestControllerAdvice
public class GlobalExceptionAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

	@ExceptionHandler(ServiceException.class)
	public ApiResult<?> businessExceptionHandler(final ServiceException serviceException) {
		ApiResult<?> apiResult = ApiResult.failed().setMessage(serviceException.getMessage());
		return apiResult;

	}

	@ExceptionHandler(Exception.class)
	public ApiResult<?> uncaughtExceptionHandler(Exception exception) {
		logger.error("uncaught exception:",exception);
		return getLoacleInternalErrorApiResult();
	}

	/**
	 * 参数校验异常
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = { BindException.class, MethodArgumentNotValidException.class})
	public ApiResult<?> validaterExceptionHandler(final Exception exception) {
		ApiResult<?> apiResult = null;
		try {
			String i18nMessage = null;
			apiResult = ApiResult.failed();
			if (exception instanceof MethodArgumentNotValidException) {
				i18nMessage = methodArgumentNotValidExceptionHandler((MethodArgumentNotValidException) exception);
			} else if (exception instanceof BindException) {
				i18nMessage = methodBindExceptionHandler((BindException) exception);
			}
			apiResult.setMessage(i18nMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			apiResult = getLoacleInternalErrorApiResult();
			e.printStackTrace();
		}
		return apiResult;

	}

	private String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
		String i18nMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
		return i18nMessage;
	}

	private String methodBindExceptionHandler(BindException exception) {
		FieldError fieldError = exception.getFieldError();
		String i18nMessage = fieldError.getDefaultMessage();
		return i18nMessage;
	}

	private ApiResult<?> getLoacleInternalErrorApiResult() {
		ApiResult<?> apiResult = ApiResult.internalError();
		return apiResult;
	}
}
