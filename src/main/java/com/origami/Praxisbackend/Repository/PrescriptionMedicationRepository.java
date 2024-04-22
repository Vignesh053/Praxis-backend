package com.origami.Praxisbackend.Repository;

import com.origami.Praxisbackend.Entity.PrescriptionMedication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PrescriptionMedicationRepository extends JpaRepository<PrescriptionMedication, Long> {

    Set<PrescriptionMedication> findAllByPrescriptionId(Long presctiptionId);
}
