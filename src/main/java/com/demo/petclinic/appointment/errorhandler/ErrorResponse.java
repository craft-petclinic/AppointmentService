package com.demo.petclinic.appointment.errorhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	// General error message about nature of error
	private String message;

	// Specific errors in API request processing
	private List<String> details;

	public ErrorResponse(String message, List<String> details) {
		this();
		this.message = message;
		this.details = details;
	}

	public ErrorResponse() {
		timestamp = LocalDateTime.now();
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the details
	 */
	public List<String> getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(List<String> details) {
		this.details = details;
	}

}