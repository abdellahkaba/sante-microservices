package com.isi.medecin.service;


import com.isi.medecin.dto.MedecinRequest;
import com.isi.medecin.dto.MedecinResponse;

import java.util.List;

public interface MedecinService {

    MedecinResponse newMedecin(MedecinRequest request);
    MedecinResponse getMedecinById(Long id);
    List<MedecinResponse> getAllMedecin();
    MedecinResponse updateMedecin(MedecinRequest request);
    void deleteMedecinById(Long id);
}
