package com.isi.dossier.service.impl;

import com.isi.dossier.dto.DossierRequest;
import com.isi.dossier.dto.DossierResponse;
import com.isi.dossier.exception.EntityNotFoundException;
import com.isi.dossier.mapper.DossierMapper;
import com.isi.dossier.model.Dossier;
import com.isi.dossier.patient.PatientClient;
import com.isi.dossier.patient.PatientResponse;
import com.isi.dossier.repository.DossierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DossierServiceImplTest {

    @Mock
    private DossierRepository repository;

    @Mock
    private DossierMapper mapper;

    @Mock
    private MessageSource messageSource;

    @Mock
    private PatientClient patientClient;

    @InjectMocks
    private DossierServiceImpl service;

    @Test
    void newDossierOK() {
        when(patientClient.findPatientById(anyLong()))
                .thenReturn(Optional.of(getPatientResponse()));
        when(mapper.toDossier(any())).thenReturn(getDossier());
        when(repository.save(any())).thenReturn(getDossier());
        when(mapper.toDossierResponse(any())).thenReturn(getDossierResponse());

        DossierResponse response = service.newDossier(getDossierRequest());

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(patientClient, times(1)).findPatientById(anyLong());
    }

    @Test
    void newDossierKO_PatientNotFound() {
        when(patientClient.findPatientById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("patient.notfound"), any(), any(Locale.class)))
                .thenReturn("Patient not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.newDossier(getDossierRequest()));

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    void getDossierByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getDossier()));
        when(mapper.toDossierResponse(any())).thenReturn(getDossierResponse());

        DossierResponse response = service.getDossierById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getDossierByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("dossier.notfound"), any(), any(Locale.class)))
                .thenReturn("Dossier not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.getDossierById(1L));

        assertEquals("Dossier not found", exception.getMessage());
    }

    @Test
    void getAllDossier() {
        when(repository.findAll()).thenReturn(List.of(getDossier()));
        when(mapper.toDossierResponseList(any())).thenReturn(List.of(getDossierResponse()));

        List<DossierResponse> list = service.getAllDossier();

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void updateDossierOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getDossier()));
        when(patientClient.findPatientById(anyLong()))
                .thenReturn(Optional.of(getPatientResponse()));
        when(repository.save(any())).thenReturn(getDossier());
        when(mapper.toDossierResponse(any())).thenReturn(getDossierResponse());

        DossierResponse response = service.updateDossier(getDossierRequest());

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(patientClient, times(1)).findPatientById(anyLong());
    }

    @Test
    void updateDossierKO_DossierNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("dossier.notfound"), any(), any(Locale.class)))
                .thenReturn("Dossier not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.updateDossier(getDossierRequest()));

        assertEquals("Dossier not found", exception.getMessage());
    }

    @Test
    void updateDossierKO_PatientNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getDossier()));
        when(patientClient.findPatientById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("patient.notfound"), any(), any(Locale.class)))
                .thenReturn("Patient not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.updateDossier(getDossierRequest()));

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    void deleteDossierByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getDossier()));

        service.deleteDossierById(1L);

        verify(repository, times(1)).delete(any());
    }

    @Test
    void deleteDossierByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("dossier.notfound"), any(), any(Locale.class)))
                .thenReturn("Dossier not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.deleteDossierById(1L));

        assertEquals("Dossier not found", exception.getMessage());
    }

    // Méthodes utilitaires
    private DossierRequest getDossierRequest() {
        DossierRequest request = new DossierRequest();
        request.setId(1L);
        request.setPatientId(1L);
        request.setDateConsultation(LocalDate.now());
        request.setCompteRendu("Compte rendu de consultation");
        return request;
    }

    private Dossier getDossier() {
        Dossier dossier = new Dossier();
        dossier.setId(1L);
        dossier.setPatientId(1L);
        dossier.setDateConsultation(LocalDate.now());
        dossier.setCompteRendu("Compte rendu de consultation");
        return dossier;
    }

    private DossierResponse getDossierResponse() {
        DossierResponse response = new DossierResponse();
        response.setId(1L);
        response.setPatientId(1L);
        response.setDateConsultation(LocalDate.of(2023, 1, 1)); // Date fixe
        response.setCompteRendu("Compte rendu de consultation");
        return response;
    }

    private PatientResponse getPatientResponse() {
        return new PatientResponse(
                1L,
                "Doe",
                "John",
                LocalDate.of(1980, 1, 1),
                "M",
                "123 Main St",
                "0102030405",
                "patient@example.com"
        );
    }
}