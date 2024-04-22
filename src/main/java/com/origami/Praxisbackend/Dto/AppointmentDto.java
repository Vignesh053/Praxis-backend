package com.origami.Praxisbackend.Dto;

import com.origami.Praxisbackend.Entity.Doctor;
import com.origami.Praxisbackend.Entity.Patient;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AppointmentDto {
    private Long id;
    private Patient patient;
    private Long patientId;
    private Doctor doctor;
    private Long doctorId;
    private LocalDateTime appointmentTime;
    private String reasonForVisit;
    private String appointmentStatus;
}
