package com.isi.rdv.medecin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "medecin-service"
)
public interface MedecinClient {
    @GetMapping("/api/v1/medecins/{medecin-id}")
    Optional<MedecinResponse> findMedecinById(@PathVariable("medecin-id") Long medecinId);
}