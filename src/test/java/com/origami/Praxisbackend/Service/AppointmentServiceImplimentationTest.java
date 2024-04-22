package com.origami.Praxisbackend.Service;




import com.origami.Praxisbackend.Dto.AppointmentDto;
import com.origami.Praxisbackend.Entity.Appointment;
import com.origami.Praxisbackend.Entity.Doctor;
import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Repository.AppointmentRepository;
import com.origami.Praxisbackend.Repository.DoctorRepopsitory;
import com.origami.Praxisbackend.Repository.PatientRepository;
import com.origami.Praxisbackend.Service.ServiceImplementation.AppointmentServiceImplimentation;
import com.origami.Praxisbackend.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplimentationTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepopsitory doctorRepopsitory;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentServiceImplimentation appointmentService;

    private Patient patient;
    private Doctor doctor;
    private AppointmentDto appointmentDto;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        patient = Patient.builder().id(1L).build();
        doctor = Doctor.builder().id(2L).build();
        appointmentDto = AppointmentDto.builder()
                .patientId(patient.getId())
                .doctorId(doctor.getId())
                .build();
        appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentTime(LocalDateTime.now())
                .build();
    }

    @Test
    void givenValidAppointmentDto_whenCreateAppointment_thenReturnAppointmentDto() {
        when(patientRepository.findById(appointmentDto.getPatientId())).thenReturn(Optional.of(patient));
        when(doctorRepopsitory.findById(appointmentDto.getDoctorId())).thenReturn(Optional.of(doctor));
        when(modelMapper.map(appointmentDto, Appointment.class)).thenReturn(appointment);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(modelMapper.map(appointment, AppointmentDto.class)).thenReturn(appointmentDto);

        AppointmentDto createdAppointment = appointmentService.createAppointment(appointmentDto);
        assertEquals(appointmentDto, createdAppointment);
    }

    @Test
    void givenInvalidPatientId_whenCreateAppointment_thenThrowResourceNotFoundException() {
        when(patientRepository.findById(appointmentDto.getPatientId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.createAppointment(appointmentDto));
    }

    @Test
    void givenInvalidDoctorId_whenCreateAppointment_thenThrowResourceNotFoundException() {
        when(patientRepository.findById(appointmentDto.getPatientId())).thenReturn(Optional.of(patient));
        when(doctorRepopsitory.findById(appointmentDto.getDoctorId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.createAppointment(appointmentDto));
    }

    @Test
    void givenPatientId_whenFindByPatientId_thenReturnAppointmentDtos() {
        Long patientId = 1L;
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(Appointment.builder().id(1L).build());
        appointmentList.add(Appointment.builder().id(2L).build());

        List<AppointmentDto> expectedAppointmentDtos = new ArrayList<>();
        expectedAppointmentDtos.add(AppointmentDto.builder().id(1L).build());
        expectedAppointmentDtos.add(AppointmentDto.builder().id(2L).build());

        when(appointmentRepository.findByPatientId(patientId)).thenReturn(appointmentList);
        when(modelMapper.map(appointmentList.get(0), AppointmentDto.class)).thenReturn(expectedAppointmentDtos.get(0));
        when(modelMapper.map(appointmentList.get(1), AppointmentDto.class)).thenReturn(expectedAppointmentDtos.get(1));

        List<AppointmentDto> appointmentDtos = appointmentService.findAppointmentsByPatientId(patientId);
        assertEquals(expectedAppointmentDtos, appointmentDtos);
    }

    @Test
    void givenDoctorId_whenFindByDoctorId_thenReturnAppointmentDtos() {
        Long doctorId = 2L;
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(Appointment.builder().id(1L).patient(patient).build());
        appointmentList.add(Appointment.builder().id(2L).patient(patient).build());

        List<AppointmentDto> expectedAppointmentDtos = new ArrayList<>();
        expectedAppointmentDtos.add(AppointmentDto.builder().id(1L).patient(patient).build());
        expectedAppointmentDtos.add(AppointmentDto.builder().id(2L).patient(patient).build());

        when(appointmentRepository.findByDoctorId(doctorId)).thenReturn(appointmentList);
        when(modelMapper.map(appointmentList.get(0), AppointmentDto.class)).thenReturn(expectedAppointmentDtos.get(0));
        when(modelMapper.map(appointmentList.get(1), AppointmentDto.class)).thenReturn(expectedAppointmentDtos.get(1));

        List<AppointmentDto> appointmentDtos = appointmentService.findAppointmentsByDoctorId(doctorId);
        assertEquals(expectedAppointmentDtos, appointmentDtos);
    }

    @Test
    void givenAppointmentId_whenUpdateStatus_thenReturnUpdatedStatus() {
        Long appointmentId = 1L;
        Appointment appointment = Appointment.builder().id(appointmentId).appointmentStatus("Pending").build();

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        String updatedStatus = appointmentService.updateStatus(appointmentId);
        assertEquals("Appointment Completed", updatedStatus);
    }

    @Test
    void givenInvalidAppointmentId_whenUpdateStatus_thenThrowResourceNotFoundException() {
        Long appointmentId = 1L;

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.updateStatus(appointmentId));
    }
}