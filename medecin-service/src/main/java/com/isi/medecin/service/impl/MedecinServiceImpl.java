package com.isi.medecin.service.impl;

import com.isi.medecin.dto.MedecinRequest;
import com.isi.medecin.dto.MedecinResponse;
import com.isi.medecin.exception.EntityExistsException;
import com.isi.medecin.exception.EntityNotFoundException;
import com.isi.medecin.mapper.MedecinMapper;
import com.isi.medecin.model.Medecin;
import com.isi.medecin.repository.MedecinRepository;
import com.isi.medecin.service.MedecinService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@Getter
@Setter
@AllArgsConstructor

public class MedecinServiceImpl implements MedecinService {

    private final MedecinRepository repository;
    private final MedecinMapper mapper;
    private final MessageSource messageSource;

    @Override
    public MedecinResponse newMedecin(MedecinRequest request) {

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("email.exists", new
                    Object[]{request.getEmail()}, Locale.getDefault()));
        }
        if (repository.findByTelephone(request.getTelephone()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("telephone.exists", new
                    Object[]{request.getTelephone()}, Locale.getDefault()));
        }

        Medecin medecin = mapper.toMedecin(request);
        var saveMedecin = repository.save(medecin);
        return mapper.toMedecinResponse(saveMedecin);
    }

    @Override
    public MedecinResponse getMedecinById(Long id) {
        return repository.findById(id)
                .map(mapper::toMedecinResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("medecin.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<MedecinResponse> getAllMedecin() {
        return mapper.toMedecinResponseList(repository.findAll());
    }

    @Override
    public MedecinResponse updateMedecin(MedecinRequest request) {
        var medecin = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("medecin.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        repository.findByEmail(request.getEmail())
                .ifPresent(existingEmail -> {
                    if (!existingEmail.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("email.exists", new Object[]{existingEmail}, Locale.getDefault() ));
                    }
                });
        repository.findByTelephone(request.getTelephone())
                .ifPresent(existingTelephone -> {
                    if (!existingTelephone.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("telephone.exists", new Object[]{existingTelephone}, Locale.getDefault()));
                    }
                });
        medecin.setNom(request.getNom());
        medecin.setPrenom(request.getPrenom());
        medecin.setEmail(request.getEmail());
        medecin.setTelephone(request.getTelephone());
        medecin.setSpecialite(request.getSpecialite());
        medecin.setAdresseCabinet(request.getAdresseCabinet());
        return mapper.toMedecinResponse(repository.save(medecin));
    }

    @Override
    public void deleteMedecinById(Long id) {
        Medecin medecin = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("medecin.notfound", new Object[]{id}, Locale.getDefault())));
        repository.delete(medecin);
    }
}
