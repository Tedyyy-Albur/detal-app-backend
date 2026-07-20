package com.example.demo.service;

import com.example.demo.dto.AppointmentRequestDTO;
import com.example.demo.dto.AppointmentResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto);
    AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto);
    AppointmentResponseDTO getAppointmentById(Long id);
    List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date);
    List<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId);
    List<AppointmentResponseDTO> getAllAppointments();
    void cancelAppointment(Long id);
}
