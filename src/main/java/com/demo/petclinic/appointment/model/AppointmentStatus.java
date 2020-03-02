package com.demo.petclinic.appointment.model;

public enum AppointmentStatus {

	BOOKED("booked"), CANCELLED("cancelled");

	private String status;

	AppointmentStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
