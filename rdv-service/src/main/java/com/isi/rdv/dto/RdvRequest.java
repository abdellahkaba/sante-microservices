package com.isi.rdv.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RdvRequest {

    private Long id;
    @NotNull(message = "Date requise")
    private LocalDateTime date;
    private String motif;
    @NotNull(message = "patient requis")
    private Long patientId;
    @NotNull(message = "Medecin requis")
    private Long medecinId;
}
