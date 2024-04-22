package com.origami.Praxisbackend.Repository;

import com.origami.Praxisbackend.Entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findAllByPatientId(Long patientId);
}
