package com.origami.Praxisbackend.Controller;

import com.origami.Praxisbackend.Dto.AppointmentDto;
import com.origami.Praxisbackend.Dto.DoctorDto;

import com.origami.Praxisbackend.Dto.PrescriptionDto;
import com.origami.Praxisbackend.Entity.Medication;
import com.origami.Praxisbackend.Service.AppointmentService;
import com.origami.Praxisbackend.Service.DoctorService;

import com.origami.Praxisbackend.Service.MedicationService;
import com.origami.Praxisbackend.Service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("doctor")
public class DoctorController {

    private DoctorService doctorService;

    private PrescriptionService prescriptionService;

    private AppointmentService appointmentService;

    private MedicationService medicationService;

    @PostMapping("/register/{id}")
    public ResponseEntity<DoctorDto> registerDoctor(@RequestBody DoctorDto doctorDto, @PathVariable Long id){
        return new ResponseEntity<>(doctorService.createDoctor(doctorDto, id), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DoctorDto> getDoctorByUserId(@PathVariable Long id){
        return new ResponseEntity<>(doctorService.fetchDoctorByUserId(id), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto){
        return new ResponseEntity<>(doctorService.updateDoctor(id, doctorDto), HttpStatus.OK);
    }


    @PostMapping("/create/prescription")
    public ResponseEntity<PrescriptionDto> createPresc(@RequestBody PrescriptionDto prescriptionDto){
        return new ResponseEntity<>(prescriptionService.createPrescription(prescriptionDto), HttpStatus.CREATED);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentList(@PathVariable Long id){
        return new ResponseEntity<>(appointmentService.findAppointmentsByDoctorId(id), HttpStatus.OK);
    }

    @GetMapping("/medications")
    public ResponseEntity<List<Medication>> getMedicines(){
        return new ResponseEntity<>(medicationService.findAllMeds(), HttpStatus.OK);
    }

    @GetMapping("/updatestatus/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id){
        return new ResponseEntity<>(appointmentService.updateStatus(id), HttpStatus.OK);
    }
}
