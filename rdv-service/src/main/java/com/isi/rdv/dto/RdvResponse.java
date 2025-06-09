package com.isi.rdv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RdvResponse {

    private Long id;
    private LocalDateTime date;
    private String motif;
    private Long patientId;
    private Long medecinId;

//    private String patientName;
//    private String patientPrenom;
//    private String patientEmail;
//    private String medecinName;
//    private String medecinPrenom;
}
