package com.isi.rdv.service.impl;


import com.isi.rdv.dto.RdvRequest;
import com.isi.rdv.dto.RdvResponse;
import com.isi.rdv.exception.EntityNotFoundException;
import com.isi.rdv.mapper.RdvMapper;
import com.isi.rdv.model.Rdv;
import com.isi.rdv.patient.PatientClient;
import com.isi.rdv.repository.RdvRepository;
import com.isi.rdv.service.RdvService;
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
public class RdvServiceImpl implements RdvService {

    private final RdvRepository repository;
    private final RdvMapper mapper;
    private final MessageSource messageSource;
    private final PatientClient patientClient;

    @Override
    public RdvResponse newRdv(RdvRequest request) {

        var patient = this.patientClient.findPatientById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("patient.notfound", new Object[]{request.getPatientId()}, Locale.getDefault())));

        Rdv rdv = mapper.toRdv(request);
        rdv.setPatientId(patient.getId());
        var saveRdv = repository.save(rdv);
        return mapper.toRdvResponse(saveRdv);
    }

    @Override
    public RdvResponse getRdvById(Long id) {
        return repository.findById(id)
                .map(mapper::toRdvResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("rdv.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<RdvResponse> getAllRdv() {
        return mapper.toRdvResponseList(repository.findAll());
    }

    @Override
    public RdvResponse updateRdv(RdvRequest request) {

        var rdv = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("rdv.notfound", new Object[]{request.getId()}, Locale.getDefault())));

        var patient = this.patientClient.findPatientById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("patient.notfound", new Object[]{request.getPatientId()}, Locale.getDefault())));

        rdv.setPatientId(patient.getId());
        rdv.setDate(request.getDate());
        rdv.setMotif(request.getMotif());
        var updateRdv = repository.save(rdv);
        return mapper.toRdvResponse(updateRdv);
    }

    @Override
    public void deleteRdvById(Long id) {
        Rdv rdv = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("rdv.notfound", new Object[]{id}, Locale.getDefault() )));
        repository.delete(rdv);

    }
}
