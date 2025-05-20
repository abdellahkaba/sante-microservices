package com.isi.patient.controller;


import com.isi.patient.dto.PatientRequest;
import com.isi.patient.dto.PatientResponse;
import com.isi.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

   private final PatientService service;

    @PostMapping("/new")
    public ResponseEntity<PatientResponse> newPatient(
            @Valid @RequestBody PatientRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newPatient(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getPatientById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientResponse>> getAllPatient(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllPatient());
    }

    @PutMapping("/update")
    public ResponseEntity<PatientResponse> updatePatient(
            @Valid @RequestBody PatientRequest request) {
        PatientResponse updatePatient = service.updatePatient(request);
        return ResponseEntity.ok(updatePatient);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatientById(@PathVariable("id") Long id) {
        service.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }
}
