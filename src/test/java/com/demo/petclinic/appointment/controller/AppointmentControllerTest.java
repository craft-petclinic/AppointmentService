package com.demo.petclinic.appointment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.petclinic.appointment.model.Appointment;
import com.demo.petclinic.appointment.services.AppointmentService;

@RunWith(SpringRunner.class)
@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private AppointmentController accountRestController;

	@MockBean
	private AppointmentService appService;

	@Before
	public void setUP() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(accountRestController).build();
	}

	@Test
	public void whenFindByAllForVET_thenAllObjectsAreReturned() throws Exception {
		Appointment app1 = new Appointment();
		app1.setId("123");
		app1.setVetId("idvet1");
		Appointment app2 = new Appointment();
		app2.setId("1234");
		app2.setVetId("idvet2");

		Mockito.when(appService.getAllAppointmentOfVet("idvet1")).thenReturn(Arrays.asList(app1, app2));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/appointment/getAllAppointmentOfVet/idvet1")
				.contentType("application/json")).andExpect(status().isOk());
	}

	@Test
	public void whenFindByAllForPET_thenAllObjectsAreReturned() throws Exception {
		Appointment app1 = new Appointment();
		app1.setId("5678");
		app1.setPetId("idpet1");
		Appointment app2 = new Appointment();
		app2.setId("8910");
		app2.setPetId("idpet2");

		Mockito.when(appService.getAllAppointmentOfPet("idpet1")).thenReturn(Arrays.asList(app1, app2));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/appointment/getAllAppointmentOfPet/idpet1")
				.contentType("application/json")).andExpect(status().isOk());
	}

//	@Test
//	public void whenDeleteById_thenStatusOk() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/appointment/cancel/123").contentType("application/json"))
//				.andExpect(status().isOk());
//	}

}
