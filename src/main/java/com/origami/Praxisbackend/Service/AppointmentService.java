package com.origami.Praxisbackend.Service;

import com.origami.Praxisbackend.Dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {

    AppointmentDto createAppointment(AppointmentDto appointmentDto);

    List<AppointmentDto> findAppointmentsByPatientId(Long patientId);

    List<AppointmentDto> findAppointmentsByDoctorId(Long doctorId);

    String updateStatus(Long appointmentId);
}
