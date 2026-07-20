package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AppointmentRequestDTO;
import com.example.demo.dto.AppointmentResponseDTO;
import com.example.demo.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Appointments", description = "Endpoints for scheduling and managing dental clinic visits")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @Operation(summary = "Schedule a new Appointment", description = "Books a new time slot for a patient with a specific service. Checks for calendar/time conflicts.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Appointment scheduled successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Time slot conflict / invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Patient or Service not found")
    })
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> createAppointment(
            @Valid @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO created = appointmentService.createAppointment(dto);
        ApiResponse<AppointmentResponseDTO> response = ApiResponse.<AppointmentResponseDTO>builder()
                .success(true)
                .message("Appointment scheduled successfully")
                .data(created)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Reschedule or Update Appointment", description = "Modifies date, time, service, status, or notes of an appointment. Re-validates time conflicts.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Time slot conflict / invalid data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO updated = appointmentService.updateAppointment(id, dto);
        ApiResponse<AppointmentResponseDTO> response = ApiResponse.<AppointmentResponseDTO>builder()
                .success(true)
                .message("Appointment updated successfully")
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Appointment by ID", description = "Retrieves information for a specific appointment booking.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> getAppointmentById(@PathVariable Long id) {
        AppointmentResponseDTO appointment = appointmentService.getAppointmentById(id);
        ApiResponse<AppointmentResponseDTO> response = ApiResponse.<AppointmentResponseDTO>builder()
                .success(true)
                .message("Appointment retrieved successfully")
                .data(appointment)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "List all Appointments", description = "Retrieves all appointments in the system.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointments list retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAllAppointments() {
        List<AppointmentResponseDTO> list = appointmentService.getAllAppointments();
        ApiResponse<List<AppointmentResponseDTO>> response = ApiResponse.<List<AppointmentResponseDTO>>builder()
                .success(true)
                .message("Appointments list retrieved successfully")
                .data(list)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date/{date}")
    @Operation(summary = "List Appointments by Date", description = "Lists all appointments scheduled on a specific date.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AppointmentResponseDTO> list = appointmentService.getAppointmentsByDate(date);
        ApiResponse<List<AppointmentResponseDTO>> response = ApiResponse.<List<AppointmentResponseDTO>>builder()
                .success(true)
                .message("Appointments retrieved successfully for date " + date)
                .data(list)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "List Appointments by Patient", description = "Retrieves the appointment booking history for a specific patient.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Patient appointments history retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAppointmentsByPatient(
            @PathVariable Long patientId) {
        List<AppointmentResponseDTO> list = appointmentService.getAppointmentsByPatient(patientId);
        ApiResponse<List<AppointmentResponseDTO>> response = ApiResponse.<List<AppointmentResponseDTO>>builder()
                .success(true)
                .message("Appointments history retrieved successfully for patient id " + patientId)
                .data(list)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel Appointment", description = "Changes an appointment's status to CANCELLED instead of permanent deletion to keep booking history.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment cancelled successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    public ResponseEntity<ApiResponse<Void>> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Appointment cancelled successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
