package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalNoteDTO {
    private Long id;
    
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private Long appointmentId;
    
    @NotBlank(message = "Note content cannot be empty")
    private String note;
    
    private LocalDateTime createdAt;
}
