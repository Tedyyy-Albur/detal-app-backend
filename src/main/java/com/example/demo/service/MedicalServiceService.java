package com.example.demo.service;

import com.example.demo.dto.MedicalServiceDTO;
import java.util.List;

public interface MedicalServiceService {
    MedicalServiceDTO createService(MedicalServiceDTO dto);
    MedicalServiceDTO updateService(Long id, MedicalServiceDTO dto);
    MedicalServiceDTO getServiceById(Long id);
    List<MedicalServiceDTO> getAllServices();
    void deleteService(Long id);
}
