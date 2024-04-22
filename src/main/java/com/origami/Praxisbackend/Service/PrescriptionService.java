package com.origami.Praxisbackend.Service;

import com.origami.Praxisbackend.Dto.PrescriptionDto;
import com.origami.Praxisbackend.Entity.Prescription;

import java.util.List;

public interface PrescriptionService {


    PrescriptionDto createPrescription(PrescriptionDto prescriptionDto);
    List<PrescriptionDto> findAllPrescByPatientId(Long patientId);


}
