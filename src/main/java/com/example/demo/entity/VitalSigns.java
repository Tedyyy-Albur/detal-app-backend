package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vital_signs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VitalSigns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "blood_pressure")
    private String bloodPressure;

    private Double weight; // in kg

    private Double height; // in meters or cm

    private Double temperature; // in celsius

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;
}
