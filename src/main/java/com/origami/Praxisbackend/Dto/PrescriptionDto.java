package com.origami.Praxisbackend.Dto;

import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Entity.PrescriptionMedication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
    private Long id;

    private Patient patient;

    private Long patientId;


    private LocalDate datePrescribed;

    private String instructions;

    Set<PMDto> pmDtoSet;
}
