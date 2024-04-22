package com.origami.Praxisbackend.Dto;

import com.origami.Praxisbackend.Entity.Medication;
import com.origami.Praxisbackend.Entity.Prescription;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PMDto {

    private Long id;


    private Long prescriptionId;

    private Medication medication;

    private Long medicationId;

    private String dosage;

    private String frequency;
    private LocalDate startDate;
    private LocalDate endDate;
}
