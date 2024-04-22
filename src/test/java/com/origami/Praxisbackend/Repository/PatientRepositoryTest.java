package com.origami.Praxisbackend.Repository;


import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    private Patient patient;
    private User user;


    @BeforeEach
    void setup() {
        user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();
        entityManager.persist(user);

        patient = Patient.builder()
                .user(user)
                .fullName("John Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .contactNumber("1234567890")
                .medicalHistory("Diabetes")
                .build();
        entityManager.persist(patient);
    }

    @Test
    void findByUserIdShouldReturnPatients() {
        List<Patient> patients = patientRepository.findByUserId(user.getId());
        assertThat(patients).isNotEmpty();
        assertThat(patients.get(0)).isEqualTo(patient);
    }
}
