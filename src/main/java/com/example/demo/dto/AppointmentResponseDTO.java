package com.example.demo.dto;

import com.example.demo.entity.AppointmentStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponseDTO {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long serviceId;
    private String serviceName;
    private BigDecimal servicePrice;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private String notes;
    private String consultationNotes;
    private BigDecimal extraCharge;
    private String extraChargeDescription;
    private List<AppointmentExtraChargeDTO> extraCharges;

    private String dentalReason;
    private Boolean hasAllergies;
    private String allergiesDetail;
    private Boolean hasSelfMedicated;
    private String selfMedicatedDetail;
    private String medicationsList;
}
