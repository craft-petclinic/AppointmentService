package com.demo.petclinic.appointment.validation;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.petclinic.appointment.config.AppointmentConfig;
import com.demo.petclinic.appointment.constants.ErrorMessageConstants;
import com.demo.petclinic.appointment.errorhandler.ValidationFailedException;
import com.demo.petclinic.appointment.model.Appointment;
import com.demo.petclinic.appointment.model.AppointmentStatus;
import com.demo.petclinic.appointment.model.PetDetails;
import com.demo.petclinic.appointment.model.VetDetails;
import com.demo.petclinic.appointment.repository.AppointmentDbRepository;

@Service
public class AppointmentValidator {

	@Autowired
	private AppointmentDbRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AppointmentConfig config;

	@Value("${api.findpet}")
	private String findPetApi;

	@Value("${api.findvet}")
	private String findVetApi;

	/**
	 * verify if the pet id and vet id is valid . If either of them is invalid then
	 * return http status 400
	 * 
	 * @param input {@link Appointment}
	 * @return true if petid & vetid is valid other wise false
	 */
	public boolean isValidPetAndVet(Appointment input) {
		ResponseEntity<VetDetails> vetDetail = restTemplate.getForEntity(findVetApi + "/" + input.getVetId(),
				VetDetails.class);
		if (vetDetail.getStatusCode() != HttpStatus.OK) {
			throw new ValidationFailedException(ErrorMessageConstants.VET_NOT_FOUND);
		}
		input.setVetName(vetDetail.getBody().getName());
		ResponseEntity<PetDetails> petDetail = restTemplate.getForEntity(findPetApi + "/" + input.getPetId(),
				PetDetails.class);
		if (petDetail.getStatusCode() != HttpStatus.OK) {
			throw new ValidationFailedException(ErrorMessageConstants.PET_NOT_FOUND);
		}
		input.setPetName(petDetail.getBody().getName());
		return true;
	}

	/**
	 * verify if the booking slot is available for choosen date and time
	 * 
	 * @param vetId pet id
	 * @param date  choosen date
	 * @return true if slot available otherwise false
	 */
	public boolean isSlotAvailable(String vetId, Date date) {
		List<Appointment> appointments = repository.findByVetIdAndAppointmentDateGreaterThan(vetId, getLocalDate(date));
		if (appointments.size() == 0)
			return true;
		long choosenTime = date.getTime();
		for (Appointment app : appointments) {
			if (AppointmentStatus.CANCELLED.getStatus().equalsIgnoreCase(app.getStatus()))
				continue;
			Date appDate = app.getAppointmentDate();
			long bookedTime = appDate.getTime();
			// considering each slot is for 30 mins
			// then we need to check that any appointment is booked for slot choosen-30(min)
			// and choosen+30(min)
			long thirtyMinutesInMS = 30 * 60 * 1000;
			if (bookedTime == choosenTime)
				return false;
			if ((bookedTime < choosenTime && bookedTime > (choosenTime - thirtyMinutesInMS))) {
				return false;
			}
			if (bookedTime > choosenTime && bookedTime < (choosenTime + thirtyMinutesInMS)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * if date is past date or time is beyond working hours then throw validation
	 * exception
	 * 
	 * @param date {@link date}
	 * @return true of input is valid otherwise false
	 */
	public boolean isInputSlotValid(Date date) {
		if (date.compareTo(Calendar.getInstance().getTime()) < 0) {
			throw new ValidationFailedException(ErrorMessageConstants.CAN_NOT_BOOK_FOR_PAST_DATE);
		}

		LocalDate localDate = getLocalDate(date);
		DayOfWeek day = localDate.getDayOfWeek();
		if (day.getValue() == DayOfWeek.SATURDAY.getValue() || day.getValue() == DayOfWeek.SUNDAY.getValue()) {
			throw new ValidationFailedException(ErrorMessageConstants.CAN_NOT_BOOK_FOR_AFTER_WORKING_HOURS);
		}

		LocalTime time = getLocalTime(date);
		LocalTime startTime = LocalTime.of(config.getStartTimeHours(), config.getStartTimeMinutes());
		LocalTime endTime = LocalTime.of(config.getEndTimeHours(), config.getEndTimeMinutes());
		if (time.isBefore(startTime) || time.isAfter(endTime)) {
			throw new ValidationFailedException(ErrorMessageConstants.CAN_NOT_BOOK_FOR_AFTER_WORKING_HOURS);
		}

		return true;

	}

	/**
	 * get the local date for given java.util.date
	 * 
	 * @param date {@link Date}
	 * @return {@link LocalDate}
	 */
	private LocalDate getLocalDate(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalDate localDate = localDateTime.toLocalDate();
		return localDate;
	}

	/**
	 * get the local date for given java.util.date
	 * 
	 * @param date {@link Date}
	 * @return {@link LocalDate}
	 */
	private LocalTime getLocalTime(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalTime localTime = localDateTime.toLocalTime();
		return localTime;
	}

}
