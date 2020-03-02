package com.demo.petclinic.appointment.services;

import java.util.List;

import com.demo.petclinic.appointment.model.Appointment;

public interface AppointmentService {

	/**
	 * book an appointment for given input data . If an appointment already exist at
	 * input time then it will throw validation failed (400) exception with message
	 * "Appointment not available for choosen slot"
	 * 
	 * @param inputData object of {@link Appointment}
	 * @return {@link Appointment}
	 */
	public Appointment bookAppointment(Appointment inputData);

	/**
	 * cancel the appointment with given id . If no appointment available with given
	 * id then status return 400
	 * 
	 * @param appointmentId appointment id
	 */
	public void cancelAppointment(String appointmentId);

	/**
	 * update the appointment If an appointment already exist at input time then it
	 * will throw validation failed (400) exception with message "Appointment not
	 * available for choosen slot"
	 * 
	 * @param inputData {@link Appointment}
	 * @return {@link Appointment}
	 */
	public Appointment updateAppointment(Appointment inputData);

	/**
	 * get all appointment for a Vet
	 * 
	 * @param vetId vet id
	 * @return List of {@link Appointment}
	 */
	public List<Appointment> getAllAppointmentOfVet(String vetId);

	/**
	 * get all appointment for a pet
	 * 
	 * @param petId pet id
	 * @return List of {@link Appointment}
	 */
	public List<Appointment> getAllAppointmentOfPet(String petId);

}
