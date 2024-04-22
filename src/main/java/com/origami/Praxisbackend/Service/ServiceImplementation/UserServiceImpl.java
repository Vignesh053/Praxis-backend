package com.origami.Praxisbackend.Service.ServiceImplementation;

import com.origami.Praxisbackend.Dto.UserDto;
import com.origami.Praxisbackend.Entity.Doctor;
import com.origami.Praxisbackend.Entity.Patient;
import com.origami.Praxisbackend.Entity.User;
import com.origami.Praxisbackend.Repository.DoctorRepopsitory;
import com.origami.Praxisbackend.Repository.PatientRepository;
import com.origami.Praxisbackend.Repository.UserRepository;
import com.origami.Praxisbackend.Service.UserService;
import com.origami.Praxisbackend.exception.EmailAlreadyExistsException;
import com.origami.Praxisbackend.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private DoctorRepopsitory doctorRepopsitory;

    private PatientRepository patientRepository;

    @Override
    public String createUser(UserDto userDto) {
        if(userRepository.existsByEmailOrUsername(userDto.getEmail(), userDto.getUsername())){
            throw new EmailAlreadyExistsException(userDto.getEmail());
        }
        userRepository.save(modelMapper.map(userDto, User.class));
        return "User Registered Successfully";
    }

    @Override
    public UserDto fetchUser(Long userId) {
        Optional<User> foundUser = userRepository.findById(userId);

        if(foundUser.isPresent()){
            return modelMapper.map(foundUser.get(), UserDto.class);
        }else {
            throw new ResourceNotFoundException("User", "User Id", String.valueOf(userId));
        }



    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", String.valueOf(id)));
        foundUser.setUsername(userDto.getUsername());
        foundUser.setEmail(userDto.getEmail());
        foundUser.setPassword(userDto.getPassword());
        return modelMapper.map(userRepository.save(foundUser), UserDto.class);
    }

    @Override
    public Long findDOCorPATID(Long userID) {

        Optional<Doctor> doctor = doctorRepopsitory.findByUserId(userID);

        if(doctor.isPresent()){
            return doctor.get().getId();
        }else {
            Optional<Patient> patient = patientRepository.findByUserId(userID).stream().findAny();
            return patient.get().getId();
        }

    }
}
