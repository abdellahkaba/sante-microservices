package com.isi.rdv.controller;


import com.isi.rdv.dto.RdvRequest;
import com.isi.rdv.dto.RdvResponse;
import com.isi.rdv.service.RdvService;
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
@RequestMapping("/api/v1/rdv")
public class RdvController {

    private final RdvService service;

    @PostMapping("/new")
    public ResponseEntity<RdvResponse> newRdv(
            @Valid @RequestBody RdvRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.newRdv(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RdvResponse> getRdvById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getRdvById(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<RdvResponse>> getAllRdv(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllRdv());
    }

    @PutMapping("update")
    public ResponseEntity<RdvResponse> updateRdv(
            @Valid @RequestBody RdvRequest request) {
        RdvResponse updateRdv = service.updateRdv(request);
        return ResponseEntity.ok(updateRdv);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRdvById(@PathVariable("id") Long id) {
        service.deleteRdvById(id);
        return ResponseEntity.noContent().build();
    }
}

