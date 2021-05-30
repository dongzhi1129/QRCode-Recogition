package com.mine.qrcode;

import java.io.IOException;

import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties
public class QrCodeRecogitionApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(QrCodeRecogitionApplication.class);

	private static final String DEFAULT_OPENCV_DLL_PATH = "libs/opencv_java451.dll";

	static {
		ClassPathResource classResource = new ClassPathResource(DEFAULT_OPENCV_DLL_PATH);
		try {
			System.load(classResource.getURI().getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Welcome to OpenCV " + Core.VERSION);
	}

	public static void main(String[] args) {
		SpringApplication.run(QrCodeRecogitionApplication.class, args);
	}

}
