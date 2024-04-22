package com.origami.Praxisbackend.Service.ServiceImplementation;

import com.origami.Praxisbackend.Dto.PMDto;
import com.origami.Praxisbackend.Dto.PrescriptionDto;
import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Entity.Prescription;
import com.origami.Praxisbackend.Entity.PrescriptionMedication;
import com.origami.Praxisbackend.Repository.MedicationRepository;
import com.origami.Praxisbackend.Repository.PatientRepository;
import com.origami.Praxisbackend.Repository.PrescriptionMedicationRepository;
import com.origami.Praxisbackend.Repository.PrescriptionRepository;
import com.origami.Praxisbackend.Service.PrescriptionService;
import com.origami.Praxisbackend.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrescriptionServiceImplimentation implements PrescriptionService {

    private ModelMapper modelMapper;

    private PrescriptionRepository prescriptionRepository;

    private PrescriptionMedicationRepository pmRepository;

    private MedicationRepository medicationRepository;

    private PatientRepository patientRepository;
    @Override
    @Transactional
    public PrescriptionDto createPrescription(PrescriptionDto prescriptionDto) {

        Prescription prescription = modelMapper.map(prescriptionDto, Prescription.class);

        prescription.setPatient(patientRepository.findById(prescriptionDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "Patient Id", String.valueOf(prescriptionDto.getPatientId()))));

        prescription.setDatePrescribed(LocalDate.now());

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        Set<PMDto> prescriptionMedicationSet = prescriptionDto.getPmDtoSet().stream()
                .map(pmDto -> {
                    PrescriptionMedication pm = modelMapper.map(pmDto, PrescriptionMedication.class);
                    pm.setPrescription(savedPrescription);
                    pm.setMedication(medicationRepository.findById(pmDto.getMedicationId())
                            .orElseThrow(() -> new ResourceNotFoundException("Medication", "Medication Id", String.valueOf(pmDto.getMedicationId()))));

                    return modelMapper.map(pmRepository.save(pm), PMDto.class);
                }).collect(Collectors.toSet());

        PrescriptionDto prescriptionDto1 = modelMapper.map(savedPrescription, PrescriptionDto.class);

        prescriptionDto1.setPmDtoSet(prescriptionMedicationSet);
        return reduceDuplicates(prescriptionDto1);
    }


    @Override
    public List<PrescriptionDto> findAllPrescByPatientId(Long patientId) {

        List<Prescription> prescriptions = prescriptionRepository.findAllByPatientId(patientId);


        List<PrescriptionDto> prescriptionDtos = prescriptions.stream().map(prescription -> {

            PrescriptionDto prescriptionDto = modelMapper.map(prescription, PrescriptionDto.class);


            Set<PrescriptionMedication> pmSet = pmRepository.findAllByPrescriptionId(prescriptionDto.getId());
            Set<PMDto> pmDtoSet = pmSet.stream()
                    .map(pm -> modelMapper.map(pm, PMDto.class))
                    .collect(Collectors.toSet());


            prescriptionDto.setPmDtoSet(pmDtoSet);
            Patient patient = prescriptionDto.getPatient();
            patient.setUser(null);

            prescriptionDto.setPatient(patient);
            return prescriptionDto;
        }).collect(Collectors.toList());

        return prescriptionDtos;
    }


    private PrescriptionDto reduceDuplicates(PrescriptionDto prescriptionDto){
        prescriptionDto.setPatient(null);
        return prescriptionDto;
    }
}
