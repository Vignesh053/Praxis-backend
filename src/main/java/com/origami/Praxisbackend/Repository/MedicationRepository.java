package com.origami.Praxisbackend.Repository;

import com.origami.Praxisbackend.Entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {


}
