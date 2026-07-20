package com.example.demo.repository;

import com.example.demo.entity.Odontogram;
import com.example.demo.entity.OdontogramType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OdontogramRepository extends JpaRepository<Odontogram, Long> {
    Optional<Odontogram> findByPatientIdAndType(Long patientId, OdontogramType type);
    List<Odontogram> findByPatientId(Long patientId);
}
