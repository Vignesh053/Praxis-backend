package com.origami.Praxisbackend.Repository;

import com.origami.Praxisbackend.Entity.Appointment;
import com.origami.Praxisbackend.Entity.Doctor;
import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Entity.User;
import com.origami.Praxisbackend.Repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = { "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect" })
class AppointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Patient patient;
    private Doctor doctor;
    private Appointment appointment;

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

        doctor = Doctor.builder()
                .user(user)
                .fullName("Jane Smith")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .specialization("Cardiology")
                .build();
        entityManager.persist(doctor);

        appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentTime(LocalDateTime.now())
                .reasonForVisit("Chest pain")
                .appointmentStatus("Scheduled")
                .build();
        entityManager.persist(appointment);
    }

    @Test
    @DisplayName("Given patient ID, when findByPatientId, should return appointments")
    void givenPatientId_whenFindByPatientId_shouldReturnAppointments() {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patient.getId());
        assertThat(appointments).isNotEmpty();
        assertThat(appointments.get(0).getPatient()).isEqualTo(patient);
    }

    @Test
    @DisplayName("Given doctor ID, when findByDoctorId, should return appointments")
    void givenDoctorId_whenFindByDoctorId_shouldReturnAppointments() {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctor.getId());
        assertThat(appointments).isNotEmpty();
        assertThat(appointments.get(0).getDoctor()).isEqualTo(doctor);
    }
}