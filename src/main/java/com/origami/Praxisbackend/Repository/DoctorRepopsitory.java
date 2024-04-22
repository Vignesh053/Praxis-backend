package com.origami.Praxisbackend.Repository;

import com.origami.Praxisbackend.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepopsitory extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUserId(Long userId);
}
