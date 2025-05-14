package com.isi.rdv.repository;

import com.isi.rdv.model.Rdv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RdvRepository extends JpaRepository<Rdv, Long> {
}
