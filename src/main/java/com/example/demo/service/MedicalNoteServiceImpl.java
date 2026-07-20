package com.example.demo.service;

import com.example.demo.dto.MedicalNoteDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.MedicalNote;
import com.example.demo.entity.Patient;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.MedicalNoteRepository;
import com.example.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalNoteServiceImpl implements MedicalNoteService {

    private final MedicalNoteRepository medicalNoteRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public MedicalNoteDTO addNote(MedicalNoteDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        Appointment appointment = null;
        if (dto.getAppointmentId() != null) {
            appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + dto.getAppointmentId()));
        }

        MedicalNote note = MedicalNote.builder()
                .patient(patient)
                .appointment(appointment)
                .note(dto.getNote())
                .createdAt(LocalDateTime.now())
                .build();

        MedicalNote saved = medicalNoteRepository.save(note);
        return mapToDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalNoteDTO> getNotesByPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        return medicalNoteRepository.findByPatientIdOrderByCreatedAtDesc(patientId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteNote(Long id) {
        MedicalNote note = medicalNoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medical note not found with id: " + id));
        medicalNoteRepository.delete(note);
    }

    private MedicalNoteDTO mapToDTO(MedicalNote note) {
        return MedicalNoteDTO.builder()
                .id(note.getId())
                .patientId(note.getPatient().getId())
                .appointmentId(note.getAppointment() != null ? note.getAppointment().getId() : null)
                .note(note.getNote())
                .createdAt(note.getCreatedAt())
                .build();
    }
}
