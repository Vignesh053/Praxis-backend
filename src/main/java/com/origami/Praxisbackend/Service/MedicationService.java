package com.origami.Praxisbackend.Service;

import com.origami.Praxisbackend.Entity.Medication;

import java.util.List;

public interface MedicationService {

    List<Medication> findAllMeds();
}
