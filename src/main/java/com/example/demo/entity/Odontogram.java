package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "odontograms", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"patient_id", "type"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Odontogram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OdontogramType type;

    // Stores JSON representation of teeth states (e.g., tooth number -> state, coloring, etc.)
    @Column(name = "teeth_state", columnDefinition = "TEXT", nullable = false)
    private String teethState;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
