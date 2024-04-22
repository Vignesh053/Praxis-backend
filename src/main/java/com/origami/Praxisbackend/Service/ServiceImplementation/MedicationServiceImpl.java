package com.origami.Praxisbackend.Service.ServiceImplementation;

import com.origami.Praxisbackend.Entity.Medication;
import com.origami.Praxisbackend.Repository.MedicationRepository;
import com.origami.Praxisbackend.Service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private MedicationRepository medicationRepository;
    @Override
    public List<Medication> findAllMeds() {
        return medicationRepository.findAll();
    }
}
