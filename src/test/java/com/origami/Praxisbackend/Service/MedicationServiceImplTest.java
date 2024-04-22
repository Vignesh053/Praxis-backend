package com.origami.Praxisbackend.Service;



import com.origami.Praxisbackend.Entity.Medication;
import com.origami.Praxisbackend.Repository.MedicationRepository;
import com.origami.Praxisbackend.Service.ServiceImplementation.MedicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicationServiceImplTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationServiceImpl medicationService;

    private List<Medication> medications;

    @BeforeEach
    void setUp() {
        medications = new ArrayList<>();
        medications.add(Medication.builder().id(1L).name("Medication 1").description("Description 1").build());
        medications.add(Medication.builder().id(2L).name("Medication 2").description("Description 2").build());
    }

    @Test
    void whenFindAllMeds_thenReturnMedicationList() {
        when(medicationRepository.findAll()).thenReturn(medications);

        List<Medication> foundMedications = medicationService.findAllMeds();
        assertEquals(medications, foundMedications);
    }
}