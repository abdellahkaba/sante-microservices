package com.isi.dossier.service.impl;

import com.isi.dossier.dto.DossierRequest;
import com.isi.dossier.dto.DossierResponse;
import com.isi.dossier.exception.EntityNotFoundException;
import com.isi.dossier.mapper.DossierMapper;
import com.isi.dossier.model.Dossier;
import com.isi.dossier.patient.PatientClient;
import com.isi.dossier.repository.DossierRepository;
import com.isi.dossier.service.DossierService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;


@Service
@AllArgsConstructor
@Getter
@Setter
public class DossierServiceImpl implements DossierService {

    private final DossierRepository repository;
    private final DossierMapper mapper;
    private final MessageSource messageSource;
    private final PatientClient patientClient;


    @Override
    public DossierResponse newDossier(DossierRequest request) {
        var patient = this.patientClient.findPatientById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("patient.notfound", new Object[]{request.getPatientId()}, Locale.getDefault())));

        Dossier dossier = mapper.toDossier(request);
        dossier.setPatientId(patient.getId());
        var savedDossier = repository.save(dossier);
        return mapper.toDossierResponse(savedDossier);

    }

    @Override
    public DossierResponse getDossierById(Long id) {
        return repository.findById(id)
                .map(mapper::toDossierResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("dossier.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<DossierResponse> getAllDossier() {
        return mapper.toDossierResponseList(repository.findAll());
    }

    @Override
    public DossierResponse updateDossier(DossierRequest request) {
        var dossier = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("dossier.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        var patient = this.patientClient.findPatientById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("patient.notfound", new Object[]{request.getPatientId()}, Locale.getDefault())));
        dossier.setDateConsultation(request.getDateConsultation());
        dossier.setCompteRendu(request.getCompteRendu());
        dossier.setPatientId(patient.getId());
        var updateDossier = repository.save(dossier);
        return mapper.toDossierResponse(updateDossier);
    }

    @Override
    public void deleteDossierById(Long id) {

        Dossier dossier = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("dossier.notfound", new Object[]{id}, Locale.getDefault() )));
        repository.delete(dossier);
    }
}

