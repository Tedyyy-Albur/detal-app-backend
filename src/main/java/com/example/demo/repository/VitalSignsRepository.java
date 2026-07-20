package com.example.demo.repository;

import com.example.demo.entity.VitalSigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VitalSignsRepository extends JpaRepository<VitalSigns, Long> {
    Optional<VitalSigns> findByPatientId(Long patientId);
}
