package com.isi.dossier.patient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "patient-service"
)
public interface PatientClient {

    @GetMapping("/api/v1/patients/{patient-id}")
    Optional<PatientResponse> findPatientById(@PathVariable("patient-id") Long patientId);
}
