package com.origami.Praxisbackend.Service;


import com.origami.Praxisbackend.Dto.PatientDto;
import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Entity.User;
import com.origami.Praxisbackend.Repository.PatientRepository;
import com.origami.Praxisbackend.Repository.UserRepository;
import com.origami.Praxisbackend.Service.ServiceImplementation.PatientServiceImplimentation;
import com.origami.Praxisbackend.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplimentationTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PatientServiceImplimentation patientService;

    private User user;
    private Patient patient;
    private PatientDto patientDto;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).build();
        patient = Patient.builder().id(1L).user(user).fullName("John Doe").dateOfBirth(LocalDate.of(1990, 1, 1)).contactNumber("1234567890").medicalHistory("Some medical history").build();
        patientDto = PatientDto.builder().fullName("John Doe").dateOfBirth(LocalDate.of(1990, 1, 1)).contactNumber("1234567890").medicalHistory("Some medical history").build();
    }

    @Test
    void givenValidPatientDto_andUserId_whenCreatePatient_thenReturnPatientDto() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(patientDto, Patient.class)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(modelMapper.map(patient, PatientDto.class)).thenReturn(patientDto);

        PatientDto createdPatientDto = patientService.createPatient(patientDto, userId);
        assertEquals(patientDto, createdPatientDto);
    }

    @Test
    void givenInvalidUserId_whenCreatePatient_thenThrowResourceNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.createPatient(patientDto, userId));
    }

    @Test
    void givenValidPatientId_andPatientDto_whenUpdatePatient_thenReturnUpdatedPatientDto() {
        Long patientId = 1L;
        PatientDto updatedPatientDto = PatientDto.builder().fullName("Updated Name").dateOfBirth(LocalDate.of(1995, 5, 10)).contactNumber("9876543210").medicalHistory("Updated medical history").build();
        Patient updatedPatient = Patient.builder().id(patientId).user(user).fullName(updatedPatientDto.getFullName()).dateOfBirth(updatedPatientDto.getDateOfBirth()).contactNumber(updatedPatientDto.getContactNumber()).medicalHistory(updatedPatientDto.getMedicalHistory()).build();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(patientRepository.save(updatedPatient)).thenReturn(updatedPatient);
        when(modelMapper.map(updatedPatient, PatientDto.class)).thenReturn(updatedPatientDto);

        PatientDto updatedDto = patientService.updatePatient(patientId, updatedPatientDto);
        assertEquals(updatedPatientDto, updatedDto);
    }

    @Test
    void givenInvalidPatientId_whenUpdatePatient_thenThrowResourceNotFoundException() {
        Long patientId = 1L;
        PatientDto updatedPatientDto = PatientDto.builder().fullName("Updated Name").dateOfBirth(LocalDate.of(1995, 5, 10)).contactNumber("9876543210").medicalHistory("Updated medical history").build();

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patientService.updatePatient(patientId, updatedPatientDto));
    }

    @Test
    void givenValidUserId_whenGetPatientsByUserId_thenReturnPatientDtoList() {
        Long userId = 1L;
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(Patient.builder().id(2L).user(user).fullName("Jane Smith").dateOfBirth(LocalDate.of(1985, 3, 15)).contactNumber("9876543210").medicalHistory("Another medical history").build());

        List<PatientDto> expectedPatientDtos = new ArrayList<>();
        expectedPatientDtos.add(patientDto);
        expectedPatientDtos.add(PatientDto.builder().fullName("Jane Smith").dateOfBirth(LocalDate.of(1985, 3, 15)).contactNumber("9876543210").medicalHistory("Another medical history").build());

        when(patientRepository.findByUserId(userId)).thenReturn(patients);
        when(modelMapper.map(patients.get(0), PatientDto.class)).thenReturn(expectedPatientDtos.get(0));
        when(modelMapper.map(patients.get(1), PatientDto.class)).thenReturn(expectedPatientDtos.get(1));

        List<PatientDto> patientDtos = patientService.getPatientsByUserId(userId);
        assertEquals(expectedPatientDtos, patientDtos);
    }
}