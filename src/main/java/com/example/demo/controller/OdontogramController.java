package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.OdontogramDTO;
import com.example.demo.entity.OdontogramType;
import com.example.demo.service.OdontogramService;
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
@RequestMapping("/api/odontograms")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Odontograms", description = "Endpoints for saving and retrieving initial and final patient odontogram diagnostic states")
public class OdontogramController {

    private final OdontogramService odontogramService;

    @PostMapping
    @Operation(summary = "Save or Update Odontogram State", description = "Saves the teeth state configuration for a patient. If the patient already has an odontogram of the specified type (INITIAL or FINAL), it updates the existing record.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Odontogram state saved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<ApiResponse<OdontogramDTO>> saveOdontogram(@Valid @RequestBody OdontogramDTO dto) {
        OdontogramDTO saved = odontogramService.saveOdontogram(dto);
        ApiResponse<OdontogramDTO> response = ApiResponse.<OdontogramDTO>builder()
                .success(true)
                .message("Odontogram state saved successfully")
                .data(saved)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}/type/{type}")
    @Operation(summary = "Get Odontogram by Patient and Type", description = "Retrieves the teeth state for a specific patient and odontogram type (INITIAL or FINAL).")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Odontogram state retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient or Odontogram not found")
    })
    public ResponseEntity<ApiResponse<OdontogramDTO>> getOdontogramByPatientAndType(
            @PathVariable Long patientId,
            @PathVariable OdontogramType type) {
        OdontogramDTO odontogram = odontogramService.getOdontogramByPatientAndType(patientId, type);
        ApiResponse<OdontogramDTO> response = ApiResponse.<OdontogramDTO>builder()
                .success(true)
                .message("Odontogram state retrieved successfully")
                .data(odontogram)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "List Odontograms by Patient", description = "Lists all saved odontogram states (INITIAL and FINAL) for a given patient.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patient odontograms list retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient not found")
    })
    public ResponseEntity<ApiResponse<List<OdontogramDTO>>> getOdontogramsByPatient(@PathVariable Long patientId) {
        List<OdontogramDTO> list = odontogramService.getOdontogramsByPatient(patientId);
        ApiResponse<List<OdontogramDTO>> response = ApiResponse.<List<OdontogramDTO>>builder()
                .success(true)
                .message("Patient odontograms list retrieved successfully")
                .data(list)
                .build();
        return ResponseEntity.ok(response);
    }
}
