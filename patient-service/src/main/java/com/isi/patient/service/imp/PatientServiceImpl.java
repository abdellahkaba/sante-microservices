package com.isi.patient.service.imp;


import com.isi.patient.dto.PatientRequest;
import com.isi.patient.dto.PatientResponse;
import com.isi.patient.exception.EntityExistsException;
import com.isi.patient.exception.EntityNotFoundException;
import com.isi.patient.mapper.PatientMapper;
import com.isi.patient.model.Patient;
import com.isi.patient.repository.PatientRepository;
import com.isi.patient.service.PatientService;
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

public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final MessageSource messageSource;
    private final PatientMapper mapper;

    @Override
    public PatientResponse newPatient(PatientRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("email.exists", new
                    Object[]{request.getEmail()}, Locale.getDefault()));
        }
        if (repository.findByTelephone(request.getTelephone()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("telephone.exists", new
                    Object[]{request.getTelephone()}, Locale.getDefault()));
        }
        Patient patient = mapper.toPatient(request);
        var savedPatient = repository.save(patient);
        return mapper.toPatientResponse(savedPatient);
    }

    @Override
    public PatientResponse getPatientById(Long id) {
        return repository.findById(id)
                .map(mapper::toPatientResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("patient.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<PatientResponse> getAllPatient() {
        return mapper.toPatientResponseList(repository.findAll());
    }

    @Override
    public PatientResponse updatePatient(PatientRequest request) {

        var patient = repository.findById(request.getId())
                        .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("patient.notfound", new Object[]{request.getId()}, Locale.getDefault())));
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
        patient.setNom(request.getNom());
        patient.setPrenom(request.getPrenom());
        patient.setDateNaissance(request.getDateNaissance());
        patient.setSexe(request.getSexe());
        patient.setAdresse(request.getAdresse());
        patient.setTelephone(request.getTelephone());
        patient.setEmail(request.getEmail());
        var updatedPatient = repository.save(patient);
        return mapper.toPatientResponse(updatedPatient);
    }

    @Override
    public void deletePatientById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("patient.notfound", new Object[]{id}, Locale.getDefault() )));
        repository.delete(patient);
    }


}
