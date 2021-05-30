package com.mine.qrcode.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qrcode.resource.path")
public class QRCodeRecogitionProperties {

	private String prefix;

	private String sourceImagePath;

	private String debugImagePath;

	private String achievedQrcodePath;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSourceImagePath() {
		return sourceImagePath;
	}

	public void setSourceImagePath(String sourceImagePath) {
		this.sourceImagePath = sourceImagePath;
	}

	public String getDebugImagePath() {
		return debugImagePath;
	}

	public void setDebugImagePath(String debugImagePath) {
		this.debugImagePath = debugImagePath;
	}

	public String getAchievedQrcodePath() {
		return achievedQrcodePath;
	}

	public void setAchievedQrcodePath(String achievedQrcodePath) {
		this.achievedQrcodePath = achievedQrcodePath;
	}

}
