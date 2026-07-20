package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PatientRequestDTO;
import com.example.demo.dto.PatientResponseDTO;
import com.example.demo.dto.VitalSignsDTO;
import com.example.demo.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Patients", description = "Endpoints for managing patient records and vital signs")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @Operation(summary = "Create a new Patient", description = "Creates a new clinical record for a patient. If it is the first registration, vital signs can also be included.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Patient clinical record created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ApiResponse<PatientResponseDTO>> createPatient(@Valid @RequestBody PatientRequestDTO dto) {
        PatientResponseDTO createdPatient = patientService.createPatient(dto);
        ApiResponse<PatientResponseDTO> response = ApiResponse.<PatientResponseDTO>builder()
                .success(true)
                .message("Patient clinical record created successfully")
                .data(createdPatient)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Patient", description = "Updates general information of a patient by their ID.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patient record updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<ApiResponse<PatientResponseDTO>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO dto) {
        PatientResponseDTO updated = patientService.updatePatient(id, dto);
        ApiResponse<PatientResponseDTO> response = ApiResponse.<PatientResponseDTO>builder()
                .success(true)
                .message("Patient record updated successfully")
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Patient by ID", description = "Retrieves complete clinical record details for a patient by their ID.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patient record retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<ApiResponse<PatientResponseDTO>> getPatientById(@PathVariable Long id) {
        PatientResponseDTO patient = patientService.getPatientById(id);
        ApiResponse<PatientResponseDTO> response = ApiResponse.<PatientResponseDTO>builder()
                .success(true)
                .message("Patient record retrieved successfully")
                .data(patient)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "List and Search Patients", description = "Lists patients with support for pagination and text search (by name or phone).")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of patients retrieved successfully")
    })
    public ResponseEntity<ApiResponse<Page<PatientResponseDTO>>> getPatients(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PatientResponseDTO> patients = patientService.getPatients(search, pageable);

        ApiResponse<Page<PatientResponseDTO>> response = ApiResponse.<Page<PatientResponseDTO>>builder()
                .success(true)
                .message("Patients list retrieved successfully")
                .data(patients)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient by ID", description = "Permanently deletes a patient and all their associated records from the clinic database.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patient record deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Patient record deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/vital-signs")
    @Operation(summary = "Update Patient Vital Signs", description = "Registers or updates the vital signs record for a specific patient.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Vital signs updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<ApiResponse<PatientResponseDTO>> updateVitalSigns(
            @PathVariable Long id,
            @Valid @RequestBody VitalSignsDTO vitalSignsDTO) {
        PatientResponseDTO updated = patientService.updateVitalSigns(id, vitalSignsDTO);
        ApiResponse<PatientResponseDTO> response = ApiResponse.<PatientResponseDTO>builder()
                .success(true)
                .message("Vital signs updated successfully")
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }
}
