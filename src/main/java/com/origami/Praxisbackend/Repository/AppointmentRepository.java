package com.origami.Praxisbackend.Repository;

import com.origami.Praxisbackend.Entity.Appointment;
import com.origami.Praxisbackend.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long id);

    List<Appointment> findByDoctorId(Long id);
}
