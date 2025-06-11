package com.isi.medecin.service.impl;


import com.isi.medecin.dto.MedecinRequest;
import com.isi.medecin.dto.MedecinResponse;
import com.isi.medecin.exception.EntityExistsException;
import com.isi.medecin.exception.EntityNotFoundException;
import com.isi.medecin.mapper.MedecinMapper;
import com.isi.medecin.model.Medecin;
import com.isi.medecin.repository.MedecinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedecinServiceImplTest {

    @Mock
    private MedecinRepository repository;

    @Mock
    private MedecinMapper mapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MedecinServiceImpl service;

    @Test
    void newMedecinOK() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.findByTelephone(anyString())).thenReturn(Optional.empty());
        when(mapper.toMedecin(any())).thenReturn(getMedecin());
        when(repository.save(any())).thenReturn(getMedecin());
        when(mapper.toMedecinResponse(any())).thenReturn(getMedecinResponse());

        MedecinResponse response = service.newMedecin(getMedecinRequest());

        assertNotNull(response);
        assertEquals("fatima.sow@hopital.sn", response.getEmail());
    }

    @Test
    void newMedecinKO_EmailExists() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(getMedecin()));
        when(messageSource.getMessage(eq("email.exists"), any(), any(Locale.class)))
                .thenReturn("Email already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> service.newMedecin(getMedecinRequest()));

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void newMedecinKO_TelephoneExists() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.findByTelephone(anyString())).thenReturn(Optional.of(getMedecin()));
        when(messageSource.getMessage(eq("telephone.exists"), any(), any(Locale.class)))
                .thenReturn("Telephone already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> service.newMedecin(getMedecinRequest()));

        assertEquals("Telephone already exists", exception.getMessage());
    }

    @Test
    void getMedecinByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getMedecin()));
        when(mapper.toMedecinResponse(any())).thenReturn(getMedecinResponse());

        MedecinResponse response = service.getMedecinById(1L);

        assertNotNull(response);
        assertEquals("fatima.sow@hopital.sn", response.getEmail());
    }

    @Test
    void getMedecinByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("medecin.notfound"), any(), any(Locale.class)))
                .thenReturn("Medecin not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.getMedecinById(1L));

        assertEquals("Medecin not found", exception.getMessage());
    }

    @Test
    void getAllMedecin() {
        when(repository.findAll()).thenReturn(List.of(getMedecin()));
        when(mapper.toMedecinResponseList(any())).thenReturn(List.of(getMedecinResponse()));

        List<MedecinResponse> list = service.getAllMedecin();

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void updateMedecinOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getMedecin()));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(getMedecin()));
        when(repository.findByTelephone(anyString())).thenReturn(Optional.of(getMedecin()));
        when(repository.save(any())).thenReturn(getMedecin());
        when(mapper.toMedecinResponse(any())).thenReturn(getMedecinResponse());

        MedecinResponse response = service.updateMedecin(getMedecinRequest());

        assertNotNull(response);
        assertEquals("fatima.sow@hopital.sn", response.getEmail());
    }

    @Test
    void updateMedecinKO_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("medecin.notfound"), any(), any(Locale.class)))
                .thenReturn("Medecin not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.updateMedecin(getMedecinRequest()));

        assertEquals("Medecin not found", exception.getMessage());
    }

    @Test
    void updateMedecinKO_EmailExists() {
        Medecin existing = getMedecin();
        existing.setId(2L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(getMedecin()));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(existing));
        when(messageSource.getMessage(eq("email.exists"), any(), any(Locale.class)))
                .thenReturn("Email already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> service.updateMedecin(getMedecinRequest()));

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void updateMedecinKO_TelephoneExists() {
        Medecin existing = getMedecin();
        existing.setId(2L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(getMedecin()));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(getMedecin())); // same id
        when(repository.findByTelephone(anyString())).thenReturn(Optional.of(existing));
        when(messageSource.getMessage(eq("telephone.exists"), any(), any(Locale.class)))
                .thenReturn("Telephone already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> service.updateMedecin(getMedecinRequest()));

        assertEquals("Telephone already exists", exception.getMessage());
    }

    @Test
    void deleteMedecinByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getMedecin()));

        service.deleteMedecinById(1L);

        verify(repository, times(1)).delete(any());
    }

    @Test
    void deleteMedecinByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("medecin.notfound"), any(), any(Locale.class)))
                .thenReturn("Medecin not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.deleteMedecinById(1L));

        assertEquals("Medecin not found", exception.getMessage());
    }

    // Méthodes utilitaires
    private MedecinRequest getMedecinRequest() {
        MedecinRequest request = new MedecinRequest();
        request.setId(1L);
        request.setNom("Nom");
        request.setPrenom("Prenom");
        request.setEmail("fatima.sow@hopital.sn");
        request.setTelephone("0102030405");
        request.setSpecialite("Cardiologie");
        request.setAdresseCabinet("123 Rue A");
        return request;
    }

    private Medecin getMedecin() {
        Medecin medecin = new Medecin();
        medecin.setId(1L);
        medecin.setNom("Nom");
        medecin.setPrenom("Prenom");
        medecin.setEmail("fatima.sow@hopital.sn");
        medecin.setTelephone("0102030405");
        medecin.setSpecialite("Cardiologie");
        medecin.setAdresseCabinet("123 Rue A");
        return medecin;
    }

    private MedecinResponse getMedecinResponse() {
        return new MedecinResponse(1L, "Nom", "Prenom", "Cardiologie", "0102030405", "fatima.sow@hopital.sn", "123 Rue A");
    }
}
