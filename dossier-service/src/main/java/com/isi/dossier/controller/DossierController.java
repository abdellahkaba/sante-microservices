package com.isi.dossier.controller;


import com.isi.dossier.dto.DossierRequest;
import com.isi.dossier.dto.DossierResponse;
import com.isi.dossier.service.DossierService;
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
@RequestMapping("/api/v1/dossiers")
public class DossierController {

    private final DossierService service;

    @PostMapping("/new")
    public ResponseEntity<DossierResponse> newDossier(
            @Valid @RequestBody DossierRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newDossier(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DossierResponse> getDossierById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getDossierById(id));
    }


    @GetMapping("/all")
    public ResponseEntity<List<DossierResponse>> getAllDossier(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllDossier());
    }

    @PutMapping("/update")
    public ResponseEntity<DossierResponse> updatePatient(
            @Valid @RequestBody DossierRequest request) {
        DossierResponse updateDossier = service.updateDossier(request);
        return ResponseEntity.ok(updateDossier);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDossierById(@PathVariable("id") Long id) {
        service.deleteDossierById(id);
        return ResponseEntity.noContent().build();
    }

}
