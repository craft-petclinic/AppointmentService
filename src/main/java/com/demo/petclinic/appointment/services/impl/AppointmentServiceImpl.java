package com.demo.petclinic.appointment.services.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.petclinic.appointment.constants.ErrorMessageConstants;
import com.demo.petclinic.appointment.errorhandler.ValidationFailedException;
import com.demo.petclinic.appointment.model.Appointment;
import com.demo.petclinic.appointment.model.AppointmentStatus;
import com.demo.petclinic.appointment.repository.AppointmentDbRepository;
import com.demo.petclinic.appointment.services.AppointmentService;
import com.demo.petclinic.appointment.validation.AppointmentValidator;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	Logger LOG = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Autowired
	private AppointmentDbRepository repository;

	@Autowired
	private AppointmentValidator validator;

	@Override
	public Appointment bookAppointment(Appointment inputData) {
		Date appointmentChoosenDate = inputData.getAppointmentDate();
		if (validator.isValidPetAndVet(inputData) && validator.isInputSlotValid(appointmentChoosenDate)
				&& validator.isSlotAvailable(inputData.getVetId(), appointmentChoosenDate)) {
			inputData.setStatus(AppointmentStatus.BOOKED.getStatus());
			return repository.insert(inputData);
		} else {
			throw new ValidationFailedException(ErrorMessageConstants.APPOINTMENT_NOT_AVAILABLE);
		}
	}

	@Override
	public void cancelAppointment(String appointmentId) {
		Optional<Appointment> optional = repository.findById(appointmentId);
		if (optional.isPresent()) {
			Appointment appointment = optional.get();
			appointment.setStatus(AppointmentStatus.CANCELLED.getStatus());
			repository.save(appointment);
		} else {
			throw new ValidationFailedException(ErrorMessageConstants.APPOINTMENT_NOT_FOUND);
		}
	}

	@Override
	public Appointment updateAppointment(Appointment inputData) {
		if (!StringUtils.isEmpty(inputData.getId())) {
			Optional<Appointment> output = repository.findById(inputData.getId());
			if (!output.isPresent())
				throw new ValidationFailedException(ErrorMessageConstants.APPOINTMENT_NOT_FOUND);
		} else {
			throw new ValidationFailedException(ErrorMessageConstants.INVALID_APPOINTMENT_ID);
		}
		if (validator.isSlotAvailable(inputData.getVetId(), inputData.getAppointmentDate())) {
			return repository.insert(inputData);
		} else {
			throw new ValidationFailedException(ErrorMessageConstants.APPOINTMENT_NOT_AVAILABLE);
		}

	}

	@Override
	public List<Appointment> getAllAppointmentOfVet(String vetId) {
		LocalDate localDate = LocalDate.now();
		List<Appointment> appointments = repository.findByVetIdAndAppointmentDateGreaterThan(vetId, localDate);
		return appointments;
	}

	@Override
	public List<Appointment> getAllAppointmentOfPet(String petId) {
		LocalDate localDate = LocalDate.now();
		List<Appointment> appointments = repository.findByPetIdAndAppointmentDateGreaterThan(petId, localDate);
		return appointments;

	}

}
