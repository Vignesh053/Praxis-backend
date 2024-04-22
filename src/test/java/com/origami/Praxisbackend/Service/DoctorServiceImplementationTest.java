package com.origami.Praxisbackend.Service;


import com.origami.Praxisbackend.Dto.DoctorDto;
import com.origami.Praxisbackend.Entity.Doctor;
import com.origami.Praxisbackend.Entity.User;
import com.origami.Praxisbackend.Repository.DoctorRepopsitory;
import com.origami.Praxisbackend.Repository.UserRepository;
import com.origami.Praxisbackend.Service.ServiceImplementation.DoctorServiceImplementation;
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
class DoctorServiceImplementationTest {

    @Mock
    private DoctorRepopsitory doctorRepopsitory;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DoctorServiceImplementation doctorService;

    private User user;
    private Doctor doctor;
    private DoctorDto doctorDto;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1L).build();
        doctor = Doctor.builder().id(1L).user(user).fullName("John").dateOfBirth(LocalDate.of(1990, 1, 1)).specialization("Cardiology").build();
        doctorDto = DoctorDto.builder().fullName("John").dateOfBirth(LocalDate.of(1990, 1, 1)).specialization("Cardiology").build();
    }

    @Test
    void givenValidDoctorDto_andUserId_whenCreateDoctor_thenReturnDoctorDto() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(doctorDto, Doctor.class)).thenReturn(doctor);
        when(doctorRepopsitory.save(doctor)).thenReturn(doctor);
        when(modelMapper.map(doctor, DoctorDto.class)).thenReturn(doctorDto);

        DoctorDto createdDoctorDto = doctorService.createDoctor(doctorDto, userId);
        assertEquals(doctorDto, createdDoctorDto);
    }

    @Test
    void givenInvalidUserId_whenCreateDoctor_thenThrowResourceNotFoundException() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.createDoctor(doctorDto, userId));
    }

    @Test
    void givenValidUserId_whenFetchDoctorByUserId_thenReturnDoctorDto() {
        Long userId = 1L;
        when(doctorRepopsitory.findByUserId(userId)).thenReturn(Optional.of(doctor));
        when(modelMapper.map(doctor, DoctorDto.class)).thenReturn(doctorDto);

        DoctorDto fetchedDoctorDto = doctorService.fetchDoctorByUserId(userId);
        assertEquals(doctorDto, fetchedDoctorDto);
    }

    @Test
    void givenInvalidUserId_whenFetchDoctorByUserId_thenThrowResourceNotFoundException() {
        Long userId = 1L;
        when(doctorRepopsitory.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.fetchDoctorByUserId(userId));
    }

    @Test
    void givenInvalidDoctorId_whenUpdateDoctor_thenThrowResourceNotFoundException() {
        Long doctorId = 1L;
        DoctorDto updatedDoctorDto = DoctorDto.builder().fullName("Updated Name").dateOfBirth(LocalDate.of(1995, 5, 10)).specialization("Updated Specialization").build();

        when(doctorRepopsitory.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.updateDoctor(doctorId, updatedDoctorDto));
    }

    @Test
    void whenFindAllDocs_thenReturnDoctorDtoList() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        doctors.add(Doctor.builder().id(2L).user(User.builder().id(2L).build()).fullName("Jane").dateOfBirth(LocalDate.of(1985, 3, 15)).specialization("Neurology").build());

        List<DoctorDto> expectedDoctorDtos = new ArrayList<>();
        expectedDoctorDtos.add(doctorDto);
        expectedDoctorDtos.add(DoctorDto.builder().fullName("Jane").dateOfBirth(LocalDate.of(1985, 3, 15)).specialization("Neurology").build());

        when(doctorRepopsitory.findAll()).thenReturn(doctors);
        when(modelMapper.map(doctors.get(0), DoctorDto.class)).thenReturn(expectedDoctorDtos.get(0));
        when(modelMapper.map(doctors.get(1), DoctorDto.class)).thenReturn(expectedDoctorDtos.get(1));

        List<DoctorDto> doctorDtos = doctorService.findAllDocs();
        assertEquals(expectedDoctorDtos, doctorDtos);
    }
}
