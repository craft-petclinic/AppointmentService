package com.demo.petclinic.appointment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.petclinic.appointment.model.Appointment;
import com.demo.petclinic.appointment.repository.AppointmentDbRepository;
import com.demo.petclinic.appointment.services.impl.AppointmentServiceImpl;
import com.demo.petclinic.appointment.validation.AppointmentValidator;

@RunWith(SpringRunner.class)
public class AppointmentServicesTest {

	@MockBean
	AppointmentDbRepository appRepository;

	@MockBean
	AppointmentValidator validator;

	@TestConfiguration
	static class AccountServiceTestConfiguration {
		@Bean
		public AppointmentService appointmentService() {
			return new AppointmentServiceImpl();
		}
	}

	@Autowired
	private AppointmentService appService;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {

		Appointment appFirst = new Appointment();
		appFirst.setId("id1");
		appFirst.setVetId("vet123");
		appFirst.setPetId("pet123");
		appFirst.setAppointmentDate(new Date(2020, 03, 24, 14, 30));

		Appointment appSecond = new Appointment();
		appSecond.setId("id2");
		appSecond.setVetId("vet123");
		appSecond.setPetId("pet1234");
		appSecond.setAppointmentDate(new Date(2020, 05, 01, 8, 30));

		Appointment appThird = new Appointment();
		appThird.setId("id3");
		appThird.setVetId("vet12345");
		appThird.setPetId("pet123");
		appThird.setAppointmentDate(new Date(2020, 06, 14, 12, 30));
		LocalDate localDate = LocalDate.now();
		Mockito.when(appRepository.findByPetIdAndAppointmentDateGreaterThan("pet123", localDate))
				.thenReturn(Arrays.asList(appFirst, appThird));
		Mockito.when(appRepository.findByVetIdAndAppointmentDateGreaterThan("vet123", localDate))
				.thenReturn(Arrays.asList(appFirst, appSecond));
		Mockito.when(appRepository.findByPetIdAndAppointmentDateGreaterThan("invalid", localDate))
				.thenReturn(new ArrayList<Appointment>());
		Mockito.when(appRepository.findByVetIdAndAppointmentDateGreaterThan("invalid", localDate))
				.thenReturn(new ArrayList<Appointment>());

	}

	@Test
	public void getVetAppointment() {
		List<Appointment> appoints = appService.getAllAppointmentOfVet("vet123");
		assertEquals(2, appoints.size());

		List<Appointment> appointsForInvalidVetID = appService.getAllAppointmentOfVet("invalid");
		assertEquals(0, appointsForInvalidVetID.size());
	}

	@Test
	public void getPetAppointment() {
		List<Appointment> appoints = appService.getAllAppointmentOfPet("pet123");
		assertEquals(2, appoints.size());

		List<Appointment> appointsForInvalidPetID = appService.getAllAppointmentOfVet("invalid");
		assertEquals(0, appointsForInvalidPetID.size());
	}

}
