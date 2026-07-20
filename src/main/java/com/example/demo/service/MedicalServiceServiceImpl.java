package com.example.demo.service;

import com.example.demo.dto.MedicalServiceDTO;
import com.example.demo.entity.MedicalService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.MedicalServiceMapper;
import com.example.demo.repository.MedicalServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalServiceServiceImpl implements MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;

    @Override
    @Transactional
    public MedicalServiceDTO createService(MedicalServiceDTO dto) {
        if (medicalServiceRepository.existsByName(dto.getName())) {
            throw new BadRequestException("Service with name '" + dto.getName() + "' already exists");
        }
        MedicalService service = MedicalServiceMapper.toEntity(dto);
        MedicalService savedService = medicalServiceRepository.save(service);
        return MedicalServiceMapper.toDTO(savedService);
    }

    @Override
    @Transactional
    public MedicalServiceDTO updateService(Long id, MedicalServiceDTO dto) {
        MedicalService service = medicalServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));

        if (!service.getName().equalsIgnoreCase(dto.getName()) && medicalServiceRepository.existsByName(dto.getName())) {
            throw new BadRequestException("Service with name '" + dto.getName() + "' already exists");
        }

        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());

        MedicalService updatedService = medicalServiceRepository.save(service);
        return MedicalServiceMapper.toDTO(updatedService);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalServiceDTO getServiceById(Long id) {
        MedicalService service = medicalServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        return MedicalServiceMapper.toDTO(service);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalServiceDTO> getAllServices() {
        return medicalServiceRepository.findAll().stream()
                .map(MedicalServiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
        MedicalService service = medicalServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        medicalServiceRepository.delete(service);
    }
}
