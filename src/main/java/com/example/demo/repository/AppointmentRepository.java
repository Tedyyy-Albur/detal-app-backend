package com.example.demo.repository;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAppointmentDate(LocalDate date);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByAppointmentDateAndAppointmentTime(LocalDate date, LocalTime time);
    boolean existsByAppointmentDateAndAppointmentTimeAndStatusNot(LocalDate date, LocalTime time, AppointmentStatus status);
}
