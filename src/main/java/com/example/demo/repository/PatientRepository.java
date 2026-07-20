package com.example.demo.repository;

import com.example.demo.entity.Patient;
import com.example.demo.entity.PatientStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone, Pageable pageable);
    Page<Patient> findByStatus(PatientStatus status, Pageable pageable);
}
