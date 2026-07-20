package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.MedicalNoteDTO;
import com.example.demo.service.MedicalNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Medical Notes", description = "Endpoints for managing patient medical and clinical treatment progress notes")
public class MedicalNoteController {

    private final MedicalNoteService medicalNoteService;

    @PostMapping
    @Operation(summary = "Add a new Medical Note", description = "Appends a new chronological clinical progress note to a patient's record.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Medical note added successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<ApiResponse<MedicalNoteDTO>> addNote(@Valid @RequestBody MedicalNoteDTO dto) {
        MedicalNoteDTO created = medicalNoteService.addNote(dto);
        ApiResponse<MedicalNoteDTO> response = ApiResponse.<MedicalNoteDTO>builder()
                .success(true)
                .message("Medical note added successfully")
                .data(created)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get Medical Notes by Patient", description = "Retrieves all chronological clinical notes registered for a specific patient, ordered by latest first.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notes list retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<ApiResponse<List<MedicalNoteDTO>>> getNotesByPatient(@PathVariable Long patientId) {
        List<MedicalNoteDTO> notes = medicalNoteService.getNotesByPatient(patientId);
        ApiResponse<List<MedicalNoteDTO>> response = ApiResponse.<List<MedicalNoteDTO>>builder()
                .success(true)
                .message("Notes list retrieved successfully")
                .data(notes)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Medical Note", description = "Deletes a clinical progress note by its ID.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Note deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Note not found")
    })
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable Long id) {
        medicalNoteService.deleteNote(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Note deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
