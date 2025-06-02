package com.isi.medecin.repository;


import com.isi.medecin.model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    Optional<Medecin> findByEmail(String email);
    Optional<Medecin> findByTelephone(String telephone);
}
