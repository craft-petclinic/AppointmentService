package com.demo.petclinic.appointment.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Appointment {

	@Id
	private String id;

	private Date createdAt = new Date();
	/** date without time zone */
	@NotNull(message = "Appointment Date can not be null")
	private Date appointmentDate;
	@NotBlank(message = "vetId can not be empty")
	private String vetId;
	private String vetName;
	@NotBlank(message = "petId can not be empty")
	private String petId;
	private String petName;
	private String status = "BOOKED";
	private int price;

	/**
	 * @return the vetName
	 */
	public String getVetName() {
		return vetName;
	}

	/**
	 * @param vetName the vetName to set
	 */
	public void setVetName(String vetName) {
		this.vetName = vetName;
	}

	/**
	 * @return the petName
	 */
	public String getPetName() {
		return petName;
	}

	/**
	 * @param petName the petName to set
	 */
	public void setPetName(String petName) {
		this.petName = petName;
	}

	/**
	 * @return the appointmentDate
	 */
	public Date getAppointmentDate() {
		return appointmentDate;
	}

	/**
	 * @param appointmentDate the appointmentDate to set
	 */
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the vetId
	 */
	public String getVetId() {
		return vetId;
	}

	/**
	 * @param vetId the vetId to set
	 */
	public void setVetId(String vetId) {
		this.vetId = vetId;
	}

	/**
	 * @return the petId
	 */
	public String getPetId() {
		return petId;
	}

	/**
	 * @param petId the petId to set
	 */
	public void setPetId(String petId) {
		this.petId = petId;
	}

}
