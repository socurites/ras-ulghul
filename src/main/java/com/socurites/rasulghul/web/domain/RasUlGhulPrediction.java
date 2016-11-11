package com.socurites.rasulghul.web.domain;

public class RasUlGhulPrediction {
	private String name;

	private float confidence;
	
	private String errorMessage;

	public RasUlGhulPrediction(String name, float confidence) {
		super();
		this.name = name;
		this.confidence = confidence;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the confidence
	 */
	public float getConfidence() {
		return confidence;
	}

	/**
	 * @param confidence
	 *            the confidence to set
	 */
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
