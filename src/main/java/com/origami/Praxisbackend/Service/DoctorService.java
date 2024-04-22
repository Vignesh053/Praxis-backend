package com.origami.Praxisbackend.Service;

import com.origami.Praxisbackend.Dto.DoctorDto;
import com.origami.Praxisbackend.Entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

    DoctorDto createDoctor(DoctorDto doctorDto, Long userId);

    DoctorDto fetchDoctorByUserId(Long id);

    DoctorDto updateDoctor(Long DoctorId, DoctorDto doctorDto);

    List<DoctorDto> findAllDocs();
}
