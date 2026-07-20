package com.example.demo.repository;

import com.example.demo.entity.MedicalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {
    Optional<MedicalService> findByName(String name);
    boolean existsByName(String name);
}
