package com.origami.Praxisbackend.Repository;
import com.origami.Praxisbackend.Entity.Doctor;
import com.origami.Praxisbackend.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DoctorRepopsitory doctorRepopsitory;

    private Doctor doctor;
    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();
        entityManager.persist(user);

        doctor = Doctor.builder()
                .user(user)
                .fullName("Jane Smith")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .specialization("Cardiology")
                .build();
        entityManager.persist(doctor);
    }

    @Test
    void findByUserIdShouldReturnDoctor() {
        Optional<Doctor> foundDoctor = doctorRepopsitory.findByUserId(user.getId());
        assertThat(foundDoctor).isPresent();
        assertThat(foundDoctor.get()).isEqualTo(doctor);
    }
}