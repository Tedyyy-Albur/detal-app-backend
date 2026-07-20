package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.MedicalServiceDTO;
import com.example.demo.service.MedicalServiceService;
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
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Services & Pricing", description = "Endpoints for managing dental treatment services and their catalog pricing")
public class MedicalServiceController {

    private final MedicalServiceService medicalServiceService;

    @PostMapping
    @Operation(summary = "Add a new Service/Treatment", description = "Creates a new entry in the treatments catalog, setting its name, description, and base price.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Service added successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Service name already exists / invalid input")
    })
    public ResponseEntity<ApiResponse<MedicalServiceDTO>> createService(@Valid @RequestBody MedicalServiceDTO dto) {
        MedicalServiceDTO created = medicalServiceService.createService(dto);
        ApiResponse<MedicalServiceDTO> response = ApiResponse.<MedicalServiceDTO>builder()
                .success(true)
                .message("Service added successfully to catalog")
                .data(created)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Service", description = "Updates details and price of a catalog service by its ID.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Service updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Service not found")
    })
    public ResponseEntity<ApiResponse<MedicalServiceDTO>> updateService(
            @PathVariable Long id,
            @Valid @RequestBody MedicalServiceDTO dto) {
        MedicalServiceDTO updated = medicalServiceService.updateService(id, dto);
        ApiResponse<MedicalServiceDTO> response = ApiResponse.<MedicalServiceDTO>builder()
                .success(true)
                .message("Service updated successfully")
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Service by ID", description = "Retrieves details of a catalog service by its ID.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Service retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Service not found")
    })
    public ResponseEntity<ApiResponse<MedicalServiceDTO>> getServiceById(@PathVariable Long id) {
        MedicalServiceDTO service = medicalServiceService.getServiceById(id);
        ApiResponse<MedicalServiceDTO> response = ApiResponse.<MedicalServiceDTO>builder()
                .success(true)
                .message("Service retrieved successfully")
                .data(service)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "List all Services", description = "Retrieves the full list of all registered dental services and prices.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Catalog list retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<MedicalServiceDTO>>> getAllServices() {
        List<MedicalServiceDTO> services = medicalServiceService.getAllServices();
        ApiResponse<List<MedicalServiceDTO>> response = ApiResponse.<List<MedicalServiceDTO>>builder()
                .success(true)
                .message("Catalog list retrieved successfully")
                .data(services)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Service from Catalog", description = "Removes a service from the catalog by its ID.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Service deleted successfully from catalog"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Service not found")
    })
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long id) {
        medicalServiceService.deleteService(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Service deleted successfully from catalog")
                .build();
        return ResponseEntity.ok(response);
    }
}
