package com.origami.Praxisbackend.Repository;

import com.origami.Praxisbackend.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByUserId(Long userId);
}
