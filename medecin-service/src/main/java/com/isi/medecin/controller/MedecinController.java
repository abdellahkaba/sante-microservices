package com.isi.medecin.controller;


import com.isi.medecin.dto.MedecinRequest;
import com.isi.medecin.dto.MedecinResponse;
import com.isi.medecin.service.MedecinService;
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
@RequestMapping("/api/v1/medecins")
public class MedecinController {
    
    private final MedecinService service;

    @PostMapping("/new")
    public ResponseEntity<MedecinResponse> newMedecin(
            @Valid @RequestBody MedecinRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newMedecin(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedecinResponse> getMedecinById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getMedecinById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedecinResponse>> getAllMedecin(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllMedecin());
    }

    @PutMapping("/update")
    public ResponseEntity<MedecinResponse> updateMedecin(
            @Valid @RequestBody MedecinRequest request) {
        MedecinResponse updateMedecin = service.updateMedecin(request);
        return ResponseEntity.ok(updateMedecin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedecinById(@PathVariable("id") Long id) {
        service.deleteMedecinById(id);
        return ResponseEntity.noContent().build();
    }
}
