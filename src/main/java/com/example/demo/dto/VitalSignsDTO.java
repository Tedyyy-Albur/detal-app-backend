package com.example.demo.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VitalSignsDTO {
    private Long id;
    private String bloodPressure;
    private Double weight;
    private Double height;
    private Double temperature;
    private LocalDateTime recordedAt;
}
