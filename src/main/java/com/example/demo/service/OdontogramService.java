package com.example.demo.service;

import com.example.demo.dto.OdontogramDTO;
import com.example.demo.entity.OdontogramType;
import java.util.List;

public interface OdontogramService {
    OdontogramDTO saveOdontogram(OdontogramDTO dto);
    OdontogramDTO getOdontogramByPatientAndType(Long patientId, OdontogramType type);
    List<OdontogramDTO> getOdontogramsByPatient(Long patientId);
}
