package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "parent_guardian")
    private String parentGuardian;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "marital_status")
    private String maritalStatus;

    private String occupation;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "current_medication", columnDefinition = "TEXT")
    private String currentMedication;

    @Column(nullable = false)
    private String phone;

    private String email;

    private String address;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PatientStatus status;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private VitalSigns vitalSigns;

    @Transient
    public int getAge() {
        if (this.birthDate == null) {
            return 0;
        }
        return java.time.Period.between(this.birthDate, java.time.LocalDate.now()).getYears();
    }
}
