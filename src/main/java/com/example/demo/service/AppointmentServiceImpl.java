package com.example.demo.service;

import com.example.demo.dto.AppointmentExtraChargeDTO;
import com.example.demo.dto.AppointmentRequestDTO;
import com.example.demo.dto.AppointmentResponseDTO;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.AppointmentExtraCharge;
import com.example.demo.entity.AppointmentStatus;
import com.example.demo.entity.MedicalService;
import com.example.demo.entity.Patient;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.AppointmentMapper;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.MedicalServiceRepository;
import com.example.demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final MedicalServiceRepository medicalServiceRepository;

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));

        MedicalService service = null;
        if (dto.getServiceId() != null) {
            service = medicalServiceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + dto.getServiceId()));
        }

        // Validate time conflict
        boolean conflict = appointmentRepository.existsByAppointmentDateAndAppointmentTimeAndStatusNot(
                dto.getAppointmentDate(), dto.getAppointmentTime(), AppointmentStatus.CANCELLED);
        if (conflict) {
            throw new BadRequestException("Time slot " + dto.getAppointmentTime() + " on " + dto.getAppointmentDate() + " is already booked.");
        }

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .service(service)
                .appointmentDate(dto.getAppointmentDate())
                .appointmentTime(dto.getAppointmentTime())
                .status(dto.getStatus() != null ? dto.getStatus() : AppointmentStatus.SCHEDULED)
                .notes(dto.getNotes())
                .build();

        if (dto.getExtraCharges() != null) {
            for (AppointmentExtraChargeDTO ecDto : dto.getExtraCharges()) {
                AppointmentExtraCharge ec = AppointmentExtraCharge.builder()
                        .appointment(appointment)
                        .amount(ecDto.getAmount())
                        .description(ecDto.getDescription())
                        .build();
                appointment.getExtraCharges().add(ec);
            }
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toResponseDTO(savedAppointment);
    }

    @Override
    @Transactional
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        if (!appointment.getAppointmentDate().equals(dto.getAppointmentDate()) ||
                !appointment.getAppointmentTime().equals(dto.getAppointmentTime())) {
            boolean conflict = appointmentRepository.existsByAppointmentDateAndAppointmentTimeAndStatusNot(
                    dto.getAppointmentDate(), dto.getAppointmentTime(), AppointmentStatus.CANCELLED);
            if (conflict) {
                throw new BadRequestException("Time slot " + dto.getAppointmentTime() + " on " + dto.getAppointmentDate() + " is already booked.");
            }
        }

        MedicalService service = null;
        if (dto.getServiceId() != null) {
            service = medicalServiceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + dto.getServiceId()));
        }

        appointment.setService(service);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        if (dto.getStatus() != null) {
            appointment.setStatus(dto.getStatus());
        }
        appointment.setNotes(dto.getNotes());
        appointment.setConsultationNotes(dto.getConsultationNotes());
        appointment.setExtraCharge(dto.getExtraCharge());
        appointment.setExtraChargeDescription(dto.getExtraChargeDescription());

        // Update questionnaire fields
        appointment.setDentalReason(dto.getDentalReason());
        appointment.setHasAllergies(dto.getHasAllergies());
        appointment.setAllergiesDetail(dto.getAllergiesDetail());
        appointment.setHasSelfMedicated(dto.getHasSelfMedicated());
        appointment.setSelfMedicatedDetail(dto.getSelfMedicatedDetail());
        appointment.setMedicationsList(dto.getMedicationsList());

        // Sync values to general Patient clinical profile
        Patient patient = appointment.getPatient();
        if (patient != null) {
            if (dto.getAllergiesDetail() != null) {
                patient.setAllergies(dto.getAllergiesDetail());
            }
            if (dto.getMedicationsList() != null) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    List<Map<String, String>> meds = mapper.readValue(dto.getMedicationsList(), new TypeReference<List<Map<String, String>>>() {});
                    if (meds != null && !meds.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (Map<String, String> med : meds) {
                            String name = med.get("name");
                            String dose = med.get("dose");
                            if (name != null && !name.trim().isEmpty()) {
                                if (sb.length() > 0) {
                                    sb.append(", ");
                                }
                                sb.append(name.trim());
                                if (dose != null && !dose.trim().isEmpty()) {
                                    sb.append(" (").append(dose.trim()).append(")");
                                }
                            }
                        }
                        if (sb.length() > 0) {
                            patient.setCurrentMedication(sb.toString());
                        } else {
                            patient.setCurrentMedication("NINGUNO");
                        }
                    } else {
                        patient.setCurrentMedication("NINGUNO");
                    }
                } catch (Exception e) {
                    patient.setCurrentMedication(dto.getMedicationsList());
                }
            }
            patientRepository.save(patient);
        }

        if (dto.getExtraCharges() != null) {
            appointment.getExtraCharges().clear();
            for (AppointmentExtraChargeDTO ecDto : dto.getExtraCharges()) {
                AppointmentExtraCharge ec = AppointmentExtraCharge.builder()
                        .appointment(appointment)
                        .amount(ecDto.getAmount())
                        .description(ecDto.getDescription())
                        .build();
                appointment.getExtraCharges().add(ec);
            }
        }

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return AppointmentMapper.toResponseDTO(updatedAppointment);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return AppointmentMapper.toResponseDTO(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date).stream()
                .map(AppointmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(AppointmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(AppointmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }
}
