package com.origami.Praxisbackend.Dto;

import com.origami.Praxisbackend.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorDto {
    private Long id;
    private User user;
    private String fullName;
    private LocalDate dateOfBirth;
    private String specialization;
}
