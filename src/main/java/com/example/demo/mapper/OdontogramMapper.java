package com.example.demo.mapper;

import com.example.demo.dto.OdontogramDTO;
import com.example.demo.entity.Odontogram;

public class OdontogramMapper {

    public static OdontogramDTO toDTO(Odontogram odontogram) {
        if (odontogram == null) {
            return null;
        }
        return OdontogramDTO.builder()
                .id(odontogram.getId())
                .patientId(odontogram.getPatient().getId())
                .type(odontogram.getType())
                .teethState(odontogram.getTeethState())
                .updatedAt(odontogram.getUpdatedAt())
                .build();
    }
}
