package com.demo.petclinic.appointment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "appointment")
public class AppointmentConfig {

	private int startTimeHours;
	private int startTimeMinutes;
	private int endTimeHours;
	private int endTimeMinutes;

	/**
	 * @return the startTimeHours
	 */
	public int getStartTimeHours() {
		return startTimeHours;
	}

	/**
	 * @param startTimeHours the startTimeHours to set
	 */
	public void setStartTimeHours(int startTimeHours) {
		this.startTimeHours = startTimeHours;
	}

	/**
	 * @return the startTimeMinutes
	 */
	public int getStartTimeMinutes() {
		return startTimeMinutes;
	}

	/**
	 * @param startTimeMinutes the startTimeMinutes to set
	 */
	public void setStartTimeMinutes(int startTimeMinutes) {
		this.startTimeMinutes = startTimeMinutes;
	}

	/**
	 * @return the endTimeHours
	 */
	public int getEndTimeHours() {
		return endTimeHours;
	}

	/**
	 * @param endTimeHours the endTimeHours to set
	 */
	public void setEndTimeHours(int endTimeHours) {
		this.endTimeHours = endTimeHours;
	}

	/**
	 * @return the endTimeMinutes
	 */
	public int getEndTimeMinutes() {
		return endTimeMinutes;
	}

	/**
	 * @param endTimeMinutes the endTimeMinutes to set
	 */
	public void setEndTimeMinutes(int endTimeMinutes) {
		this.endTimeMinutes = endTimeMinutes;
	}

}
