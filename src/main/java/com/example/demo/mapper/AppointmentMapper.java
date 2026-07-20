package com.example.demo.mapper;

import com.example.demo.dto.AppointmentExtraChargeDTO;
import com.example.demo.dto.AppointmentResponseDTO;
import com.example.demo.entity.Appointment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentMapper {

    public static AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        Long serviceId = appointment.getService() != null ? appointment.getService().getId() : null;
        String serviceName = appointment.getService() != null ? appointment.getService().getName() : null;
        BigDecimal servicePrice = appointment.getService() != null ? appointment.getService().getPrice() : null;

        List<AppointmentExtraChargeDTO> ecList = new ArrayList<>();
        if (appointment.getExtraCharges() != null) {
            ecList = appointment.getExtraCharges().stream()
                .map(ec -> AppointmentExtraChargeDTO.builder()
                    .id(ec.getId())
                    .amount(ec.getAmount())
                    .description(ec.getDescription())
                    .build())
                .collect(Collectors.toList());
        }

        // Backward compatibility
        if (ecList.isEmpty() && appointment.getExtraCharge() != null) {
            ecList.add(AppointmentExtraChargeDTO.builder()
                .amount(appointment.getExtraCharge())
                .description(appointment.getExtraChargeDescription() != null ? appointment.getExtraChargeDescription() : "Cargo adicional")
                .build());
        }

        return AppointmentResponseDTO.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getName())
                .serviceId(serviceId)
                .serviceName(serviceName)
                .servicePrice(servicePrice)
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .notes(appointment.getNotes())
                .consultationNotes(appointment.getConsultationNotes())
                .extraCharge(appointment.getExtraCharge())
                .extraChargeDescription(appointment.getExtraChargeDescription())
                .extraCharges(ecList)
                .dentalReason(appointment.getDentalReason())
                .hasAllergies(appointment.getHasAllergies())
                .allergiesDetail(appointment.getAllergiesDetail())
                .hasSelfMedicated(appointment.getHasSelfMedicated())
                .selfMedicatedDetail(appointment.getSelfMedicatedDetail())
                .medicationsList(appointment.getMedicationsList())
                .build();
    }
}
