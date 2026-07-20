package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private MedicalService service;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "consultation_notes", columnDefinition = "TEXT")
    private String consultationNotes;

    @Column(name = "extra_charge", precision = 10, scale = 2)
    private BigDecimal extraCharge;

    @Column(name = "extra_charge_description")
    private String extraChargeDescription;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AppointmentExtraCharge> extraCharges = new ArrayList<>();

    @Column(name = "dental_reason", columnDefinition = "TEXT")
    private String dentalReason;

    @Column(name = "has_allergies")
    private Boolean hasAllergies;

    @Column(name = "allergies_detail", columnDefinition = "TEXT")
    private String allergiesDetail;

    @Column(name = "has_self_medicated")
    private Boolean hasSelfMedicated;

    @Column(name = "self_medicated_detail", columnDefinition = "TEXT")
    private String selfMedicatedDetail;

    @Column(name = "medications_list", columnDefinition = "TEXT")
    private String medicationsList;
}
