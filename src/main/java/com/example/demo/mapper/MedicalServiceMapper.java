package com.example.demo.mapper;

import com.example.demo.dto.MedicalServiceDTO;
import com.example.demo.entity.MedicalService;

public class MedicalServiceMapper {

    public static MedicalServiceDTO toDTO(MedicalService service) {
        if (service == null) {
            return null;
        }
        return MedicalServiceDTO.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .build();
    }

    public static MedicalService toEntity(MedicalServiceDTO dto) {
        if (dto == null) {
            return null;
        }
        return MedicalService.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }
}
