package com.example.demo.service;

import com.example.demo.dto.MedicalNoteDTO;
import java.util.List;

public interface MedicalNoteService {
    MedicalNoteDTO addNote(MedicalNoteDTO dto);
    List<MedicalNoteDTO> getNotesByPatient(Long patientId);
    void deleteNote(Long id);
}
