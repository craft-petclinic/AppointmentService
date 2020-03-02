package com.demo.petclinic.appointment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.petclinic.appointment.model.Appointment;

@Repository
public interface AppointmentDbRepository extends MongoRepository<Appointment, String> {

	@Query("{'vetId' : ?0,'appointmentDate' : {$gte : ?1}}")
	List<Appointment> findByVetIdAndAppointmentDateGreaterThan(String vetId, LocalDate date);

	@Query("{'petId' : ?0,'appointmentDate' : {$gte : ?1}}")
	List<Appointment> findByPetIdAndAppointmentDateGreaterThan(String petId, LocalDate date);
//	
//	@Query("{'vetId' : ?0,'appointmentDate' : {$gte : ?1},'status' : ?2}")
//	List<Appointment> findByVetIdAndBookedAppointmentDateGreaterThan(String vetId, LocalDate date, String status);
//
//	@Query("{'petId' : ?0,'appointmentDate' : {$gte : ?1},'status' : ?2}")
//	List<Appointment> findByPetIdAndBookedAppointmentDateGreaterThan(String petId, LocalDate date, String status);
//	
//	
}
