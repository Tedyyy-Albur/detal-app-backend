package com.example.demo.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentExtraChargeDTO {
    private Long id;
    private BigDecimal amount;
    private String description;
}
