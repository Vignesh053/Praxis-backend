package com.origami.Praxisbackend.Controller;

import com.origami.Praxisbackend.Dto.AppointmentDto;
import com.origami.Praxisbackend.Dto.DoctorDto;
import com.origami.Praxisbackend.Dto.PatientDto;
import com.origami.Praxisbackend.Dto.PrescriptionDto;
import com.origami.Praxisbackend.Service.AppointmentService;
import com.origami.Praxisbackend.Service.DoctorService;
import com.origami.Praxisbackend.Service.PatientService;
import com.origami.Praxisbackend.Service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("patient")
@AllArgsConstructor
public class PatientController {

    private PatientService patientService;

    private AppointmentService appointmentService;

    private PrescriptionService prescriptionService;

    private DoctorService doctorService;

    @PostMapping("/create/{id}")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto, @PathVariable Long id){
        return new ResponseEntity<>(patientService.createPatient(patientDto, id), HttpStatus.CREATED);
    }

    @GetMapping("/fetchlist/{id}")
    public ResponseEntity<List<PatientDto>> fetchPatient(@PathVariable Long id){
        return new ResponseEntity<>(patientService.getPatientsByUserId(id), HttpStatus.OK);
    }

    @PostMapping("/createappointment")
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentDto appointmentDto){
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/getappointments/{id}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(@PathVariable Long id){
        return new ResponseEntity<>(appointmentService.findAppointmentsByPatientId(id), HttpStatus.OK);
    }

    @GetMapping("/getprescriptions/{id}")
    public ResponseEntity<List<PrescriptionDto>> getAllPrescription(@PathVariable Long id){
        return new ResponseEntity<>(prescriptionService.findAllPrescByPatientId(id), HttpStatus.OK);
    }

    @GetMapping("/getdoctors")
    public ResponseEntity<List<DoctorDto>> fetchdoctors(){
        return new ResponseEntity<>(doctorService.findAllDocs(), HttpStatus.OK);
    }
}
