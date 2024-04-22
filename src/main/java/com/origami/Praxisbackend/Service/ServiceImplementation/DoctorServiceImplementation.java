package com.origami.Praxisbackend.Service.ServiceImplementation;

import com.origami.Praxisbackend.Dto.DoctorDto;
import com.origami.Praxisbackend.Entity.Doctor;
import com.origami.Praxisbackend.Entity.User;
import com.origami.Praxisbackend.Repository.DoctorRepopsitory;
import com.origami.Praxisbackend.Repository.UserRepository;
import com.origami.Praxisbackend.Service.DoctorService;
import com.origami.Praxisbackend.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImplementation implements DoctorService {

    private DoctorRepopsitory doctorRepopsitory;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", String.valueOf(userId)));
        Doctor doctorToSave = modelMapper.map(doctorDto, Doctor.class);
        doctorToSave.setUser(user);
        return modelMapper.map(doctorRepopsitory.save(doctorToSave), DoctorDto.class);
    }

    @Override
    public DoctorDto fetchDoctorByUserId(Long id) {
        Doctor doctor = doctorRepopsitory.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "User Id", String.valueOf(id)));

        return modelMapper.map(doctor, DoctorDto.class);
    }

    @Override
    public DoctorDto updateDoctor(Long doctorId, DoctorDto doctorDto) {

        Doctor doctor = doctorRepopsitory.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "Doctor Id", String.valueOf(doctorId)));

        doctor.setFullName(doctorDto.getFullName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setDateOfBirth(doctorDto.getDateOfBirth());

        return modelMapper.map(doctorRepopsitory.save(doctor), DoctorDto.class);
    }

    @Override
    public List<DoctorDto> findAllDocs() {
        List<Doctor> doctors = doctorRepopsitory.findAll();

        return doctors.stream().map(doc -> modelMapper.map(doc, DoctorDto.class)).collect(Collectors.toList());
    }
}
