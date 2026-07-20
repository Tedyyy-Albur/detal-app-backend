package com.example.demo.service;

import com.example.demo.dto.OdontogramDTO;
import com.example.demo.entity.Odontogram;
import com.example.demo.entity.OdontogramType;
import com.example.demo.entity.Patient;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.OdontogramMapper;
import com.example.demo.repository.OdontogramRepository;
import com.example.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OdontogramServiceImpl implements OdontogramService {

    private final OdontogramRepository odontogramRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public OdontogramDTO saveOdontogram(OdontogramDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        Optional<Odontogram> existing = odontogramRepository.findByPatientIdAndType(dto.getPatientId(), dto.getType());

        Odontogram odontogram;
        if (existing.isPresent()) {
            odontogram = existing.get();
            odontogram.setTeethState(dto.getTeethState());
            odontogram.setUpdatedAt(LocalDateTime.now());
        } else {
            odontogram = Odontogram.builder()
                    .patient(patient)
                    .type(dto.getType())
                    .teethState(dto.getTeethState())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }

        Odontogram saved = odontogramRepository.save(odontogram);
        return OdontogramMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OdontogramDTO getOdontogramByPatientAndType(Long patientId, OdontogramType type) {
        // Ensure patient exists first
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        Odontogram odontogram = odontogramRepository.findByPatientIdAndType(patientId, type)
                .orElseThrow(() -> new ResourceNotFoundException("Odontogram of type " + type + " not found for patient id: " + patientId));
        return OdontogramMapper.toDTO(odontogram);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OdontogramDTO> getOdontogramsByPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        return odontogramRepository.findByPatientId(patientId).stream()
                .map(OdontogramMapper::toDTO)
                .collect(Collectors.toList());
    }
}
