package com.origami.Praxisbackend.Service.ServiceImplementation;

import com.origami.Praxisbackend.Dto.PatientDto;
import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Entity.User;
import com.origami.Praxisbackend.Repository.PatientRepository;
import com.origami.Praxisbackend.Repository.UserRepository;
import com.origami.Praxisbackend.Service.PatientService;

import com.origami.Praxisbackend.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImplimentation implements PatientService {

    private PatientRepository patientRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public PatientDto createPatient(PatientDto patientDto, Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", String.valueOf(id)));

        Patient patientToSave = modelMapper.map(patientDto, Patient.class);

        patientToSave.setUser(user);

        return modelMapper.map(patientRepository.save(patientToSave), PatientDto.class);
    }

    @Override
    public PatientDto updatePatient(Long patientId,PatientDto patientDto) {
        Patient foundPatient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "Patient Id", String.valueOf(patientId)));
        foundPatient.setContactNumber(patientDto.getContactNumber());
        foundPatient.setFullName(patientDto.getFullName());
        foundPatient.setMedicalHistory(patientDto.getMedicalHistory());
        foundPatient.setDateOfBirth(patientDto.getDateOfBirth());
        return modelMapper.map(patientRepository.save(foundPatient), PatientDto.class);
    }

    @Override
    public List<PatientDto> getPatientsByUserId(Long userId) {
        List<Patient> patients= patientRepository.findByUserId(userId);

        List<PatientDto> patientDtos = patients.stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class)).collect(Collectors.toList());


        return patientDtos;

    }

    @Override
    public List<PatientDto> getPatientsByDoctorId(Long doctorId) {
        return null;
    }


}
