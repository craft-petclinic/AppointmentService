package com.demo.petclinic.appointment.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.petclinic.appointment.errorhandler.RecordNotFoundException;
import com.demo.petclinic.appointment.model.Appointment;
import com.demo.petclinic.appointment.services.AppointmentService;
import com.google.gson.Gson;

/**
 * an api for appointment services which manages the appointment booking
 * 
 * @author sarveshkumar
 *
 */
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	private static final Logger LOG = LoggerFactory.getLogger(AppointmentController.class);

	@Autowired
	private AppointmentService appointmentService;

	@PostMapping("/book")
	public ResponseEntity<Appointment> bookAppointment(@RequestBody @Valid Appointment model) {
		LOG.info("input data for booking appointment is " + new Gson().toJson(model));
		Appointment appoint = appointmentService.bookAppointment(model);
		return new ResponseEntity<Appointment>(appoint, HttpStatus.OK);
	}

	@GetMapping("/getAllAppointmentOfVet/{vetId}")
	public ResponseEntity<List<Appointment>> getAppointmentForVet(@PathVariable("vetId") @NotBlank String vetId) {
		LOG.info("Find appointment for vet id  " + vetId);
		List<Appointment> appoints = appointmentService.getAllAppointmentOfVet(vetId);
		if (appoints == null || appoints.size() == 0) {
			throw new RecordNotFoundException("No Appointment found");
		}
		return new ResponseEntity<List<Appointment>>(appoints, HttpStatus.OK);
	}

	@GetMapping("/getAllAppointmentOfPet/{petId}")
	public ResponseEntity<List<Appointment>> getAllAppointmentOfPet(@PathVariable("petId") @NotBlank String petId) {
		LOG.info("Find appointment for pet id  " + petId);
		List<Appointment> appoints = appointmentService.getAllAppointmentOfPet(petId);
		if (appoints == null || appoints.size() == 0) {
			throw new RecordNotFoundException("No Appointment Found");
		}
		return new ResponseEntity<List<Appointment>>(appoints, HttpStatus.OK);
	}

	@PostMapping("/cancel")
	public ResponseEntity<String> cancelAppointment(@RequestParam @NotBlank String appointmentId) {
		LOG.info("Cancel appointment with id  " + appointmentId);
		appointmentService.cancelAppointment(appointmentId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
