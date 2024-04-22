package com.origami.Praxisbackend.Service.ServiceImplementation;

import com.origami.Praxisbackend.Dto.AppointmentDto;
import com.origami.Praxisbackend.Entity.Appointment;
import com.origami.Praxisbackend.Entity.Doctor;
import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Repository.AppointmentRepository;
import com.origami.Praxisbackend.Repository.DoctorRepopsitory;
import com.origami.Praxisbackend.Repository.PatientRepository;
import com.origami.Praxisbackend.Service.AppointmentService;
import com.origami.Praxisbackend.Service.PatientService;
import com.origami.Praxisbackend.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImplimentation  implements AppointmentService {

    private ModelMapper modelMapper;

    private AppointmentRepository appointmentRepository;

    private DoctorRepopsitory doctorRepopsitory;

    private PatientRepository patientRepository;
    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        Patient patient = patientRepository.findById(appointmentDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "Patient Id", String.valueOf(appointmentDto.getPatientId())));

        Doctor doctor = doctorRepopsitory.findById(appointmentDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "Doctor Id", String.valueOf(appointmentDto.getDoctorId())));

        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(LocalDateTime.now());
        return modelMapper.map(appointmentRepository.save(appointment), AppointmentDto.class);
    }

    @Override
    public List<AppointmentDto> findAppointmentsByPatientId(Long patientId) {
        List<Appointment> appointmentList = appointmentRepository.findByPatientId(patientId);

        List<AppointmentDto> appointmentDtos = appointmentList.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class)).collect(Collectors.toList());
        return appointmentDtos;
    }

    @Override
    public List<AppointmentDto> findAppointmentsByDoctorId(Long doctorId) {
        List<Appointment> appointmentList = appointmentRepository.findByDoctorId(doctorId);

        List<AppointmentDto> appointmentDtos = appointmentList.stream()
                .map(appointment -> {
                    Patient patient = appointment.getPatient();

                    patient.setUser(null);

                    appointment.setPatient(patient);

                    appointment.setDoctor(null);

                    return modelMapper.map(appointment, AppointmentDto.class);
                }).collect(Collectors.toList());
        return appointmentDtos;
    }

    @Override
    public String updateStatus(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "Appointment Id", String.valueOf(appointmentId)));

        appointment.setAppointmentStatus("Completed");

        appointmentRepository.save(appointment);

        return "Appointment Completed";
    }
}
