package com.isi.rdv.service.impl;

import com.isi.rdv.dto.RdvRequest;
import com.isi.rdv.dto.RdvResponse;
import com.isi.rdv.exception.EntityNotFoundException;
import com.isi.rdv.mapper.RdvMapper;
import com.isi.rdv.medecin.MedecinClient;
import com.isi.rdv.medecin.MedecinResponse;
import com.isi.rdv.model.Rdv;
import com.isi.rdv.patient.PatientClient;
import com.isi.rdv.patient.PatientResponse;
import com.isi.rdv.repository.RdvRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RdvServiceImplTest {

    @Mock
    private RdvRepository repository;
    @Mock
    private RdvMapper mapper;
    @Mock
    private MessageSource messageSource;
    @Mock
    private PatientClient patientClient;
    @Mock
    private MedecinClient medecinClient;
    @InjectMocks
    private RdvServiceImpl service;

    @Test
    void newRdvOK(){

        when(patientClient.findPatientById(anyLong()))
                .thenReturn(Optional.of(getPatientResponse()));
        when(medecinClient.findMedecinById(anyLong()))
                .thenReturn(Optional.of(getMedecinResponse()));
        when(mapper.toRdv(any())).thenReturn(getRdv());

        when(repository.save(any())).thenReturn(getRdv());
        when(mapper.toRdvResponse(any())).thenReturn(getRdvResponse());

        RdvResponse response = service.newRdv(getRdvRequest());

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(patientClient, times(1)).findPatientById(anyLong());
        verify(medecinClient, times(1)).findMedecinById(anyLong());

    }

    @Test
    void newRdvKO_PatientNotFound() {
        when(patientClient.findPatientById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("patient.notfound"), any(), any(Locale.class)))
                .thenReturn("Patient not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.newRdv(getRdvRequest()));

        assertEquals("Patient not found", exception.getMessage());
    }


    @Test
    void newRdvKO_MedecinNotFound() {
        when(patientClient.findPatientById(anyLong())).thenReturn(Optional.of(getPatientResponse()));
        when(medecinClient.findMedecinById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("medecin.notfound"), any(), any(Locale.class)))
                .thenReturn("Medecin not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.newRdv(getRdvRequest()));

        assertEquals("Medecin not found", exception.getMessage());
    }

    @Test
    void getRdvByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getRdv()));
        when(mapper.toRdvResponse(any())).thenReturn(getRdvResponse());

        RdvResponse response = service.getRdvById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getRdvByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("rdv.notfound"), any(), any(Locale.class)))
                .thenReturn("Rdv not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.getRdvById(1L));

        assertEquals("Rdv not found", exception.getMessage());
    }

    @Test
    void getAllRdv() {
        when(repository.findAll()).thenReturn(List.of(getRdv()));
        when(mapper.toRdvResponseList(any())).thenReturn(List.of(getRdvResponse()));

        List<RdvResponse> list = service.getAllRdv();

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void updateRdvOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getRdv()));
        when(patientClient.findPatientById(anyLong()))
                .thenReturn(Optional.of(getPatientResponse()));

        when(medecinClient.findMedecinById(anyLong()))
                .thenReturn(Optional.of(getMedecinResponse()));

        when(repository.save(any())).thenReturn(getRdv());
        when(mapper.toRdvResponse(any())).thenReturn(getRdvResponse());

        RdvResponse response = service.updateRdv(getRdvRequest());

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(patientClient, times(1)).findPatientById(anyLong());
        verify(medecinClient, times(1)).findMedecinById(anyLong());
    }

    @Test
    void updateRdvKO_RdvNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("rdv.notfound"), any(), any(Locale.class)))
                .thenReturn("Rdv not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.updateRdv(getRdvRequest()));

        assertEquals("Rdv not found", exception.getMessage());
    }



    @Test
    void updateRdvKO_PatientNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getRdv()));
        when(patientClient.findPatientById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("patient.notfound"), any(), any(Locale.class)))
                .thenReturn("Patient not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.updateRdv(getRdvRequest()));

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    void updateRdvKO_MedecinNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getRdv()));
        when(patientClient.findPatientById(anyLong())).thenReturn(Optional.of(getPatientResponse()));
        when(medecinClient.findMedecinById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("medecin.notfound"), any(), any(Locale.class)))
                .thenReturn("Medecin not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.updateRdv(getRdvRequest()));

        assertEquals("Medecin not found", exception.getMessage());
    }

    @Test
    void deleteRdvByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getRdv()));

        service.deleteRdvById(1L);

        verify(repository, times(1)).delete(any());
    }

    @Test
    void deleteRdvByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("rdv.notfound"), any(), any(Locale.class)))
                .thenReturn("Rdv not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.deleteRdvById(1L));

        assertEquals("Rdv not found", exception.getMessage());
    }

    private Rdv getRdv() {
        Rdv rdv = new Rdv();
        rdv.setId(1L);
        rdv.setPatientId(1L);
        rdv.setMedecinId(1L);
        rdv.setDate(LocalDateTime.parse("2024-06-19T11:00"));
        rdv.setMotif("Examen de peau");
        return rdv;
    }

    private RdvResponse getRdvResponse() {
        RdvResponse response = new RdvResponse();
        response.setId(1L);
        response.setPatientId(1L);
        response.setMedecinId(1L);
        response.setDate(LocalDateTime.parse("2024-06-19T11:00"));
        response.setMotif("Examen de peau");
        return response;
    }

    private RdvRequest getRdvRequest() {
        RdvRequest request = new RdvRequest();
        request.setId(1L);
        request.setPatientId(1L);
        request.setMedecinId(1L);
        request.setDate(LocalDateTime.parse("2024-06-19T11:00"));
        request.setMotif("Examen de peau");
        return request;

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

    private MedecinResponse getMedecinResponse() {
        return new MedecinResponse(
                1L,
                "Diop",
                "Cheikh",
                "Chirugie Orthopédique",
                "77 654 32 10",
                "cheikh.diop@hopital.sn",
                "Pavillon Traumatologie, Hôpital Fann"
        );
    }

}