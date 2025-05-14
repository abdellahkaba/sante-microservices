package com.isi.dossier.service;

import com.isi.dossier.dto.DossierRequest;
import com.isi.dossier.dto.DossierResponse;

import java.util.List;

public interface DossierService {

    DossierResponse newDossier(DossierRequest request);
    DossierResponse getDossierById(Long id);
    List<DossierResponse> getAllDossier();
    DossierResponse updateDossier(DossierRequest request);
    void deleteDossierById(Long id);
}
