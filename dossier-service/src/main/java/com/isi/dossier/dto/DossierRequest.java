package com.isi.dossier.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DossierRequest {

    private Long id;
    @NotNull(message = "date requise")
    private LocalDate dateConsultation;
    private String compteRendu;
    @NotNull(message = "patient requis")
    private Long patientId;
}
