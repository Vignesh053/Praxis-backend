package com.origami.Praxisbackend.Service;

import com.origami.Praxisbackend.Dto.PatientDto;
import com.origami.Praxisbackend.Entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    PatientDto createPatient(PatientDto patientDto, Long id);

    PatientDto updatePatient(Long patientId, PatientDto patientDto);

    List<PatientDto> getPatientsByUserId(Long userId);

    List<PatientDto> getPatientsByDoctorId(Long doctorId);
}
