package com.isi.dossier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DossierResponse {

    private Long id;
    private LocalDate dateConsultation;
    private String compteRendu;
    private Long patientId;
}
