package com.isi.patient.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientRequest {

    private Long id;
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le prenom est obligatoire")
    private String prenom;
    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;
    @NotBlank(message = "Le sexe est obligatoire")
    private String sexe;
    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;
    @NotBlank(message = "Le telephone est obligatoire")
//    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Le numéro de téléphone doit être valide (ex : +1234567890)")
    private String telephone;
    @Email(message = "L'email est invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;
}
