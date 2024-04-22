package com.origami.Praxisbackend.Dto;

import com.origami.Praxisbackend.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PatientDto {

    private Long id;
    private User user;
    private String fullName;
    private LocalDate dateOfBirth;
    private String contactNumber;
    private String medicalHistory;
}
