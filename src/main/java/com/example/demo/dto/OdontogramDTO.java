package com.example.demo.dto;

import com.example.demo.entity.OdontogramType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OdontogramDTO {

    private Long id;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Odontogram type is required")
    private OdontogramType type;

    @NotBlank(message = "Teeth state JSON is required")
    private String teethState; // JSON string

    private LocalDateTime updatedAt;
}
