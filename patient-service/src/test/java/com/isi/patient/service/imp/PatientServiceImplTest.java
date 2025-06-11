package com.isi.patient.service.imp;

import com.isi.patient.dto.PatientRequest;
import com.isi.patient.dto.PatientResponse;
import com.isi.patient.exception.EntityExistsException;
import com.isi.patient.exception.EntityNotFoundException;
import com.isi.patient.mapper.PatientMapper;
import com.isi.patient.model.Patient;
import com.isi.patient.repository.PatientRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository repository;

    @Mock
    private PatientMapper mapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private PatientServiceImpl service;

    @Test
    void newPatientOK() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.findByTelephone(anyString())).thenReturn(Optional.empty());
        when(mapper.toPatient(any())).thenReturn(getPatient());
        when(repository.save(any())).thenReturn(getPatient());
        when(mapper.toPatientResponse(any())).thenReturn(getPatientResponse());

        PatientResponse response = service.newPatient(getPatientRequest());

        assertNotNull(response);
        assertEquals("patient@example.com", response.getEmail());
    }

    @Test
    void newPatientKO_EmailExists() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(getPatient()));
        when(messageSource.getMessage(eq("email.exists"), any(), any(Locale.class)))
                .thenReturn("Email already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> service.newPatient(getPatientRequest()));

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void newPatientKO_TelephoneExists() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(repository.findByTelephone(anyString())).thenReturn(Optional.of(getPatient()));
        when(messageSource.getMessage(eq("telephone.exists"), any(), any(Locale.class)))
                .thenReturn("Telephone already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> service.newPatient(getPatientRequest()));

        assertEquals("Telephone already exists", exception.getMessage());
    }

    @Test
    void getPatientByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getPatient()));
        when(mapper.toPatientResponse(any())).thenReturn(getPatientResponse());

        PatientResponse response = service.getPatientById(1L);

        assertNotNull(response);
        assertEquals("patient@example.com", response.getEmail());
    }

    @Test
    void getPatientByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("patient.notfound"), any(), any(Locale.class)))
                .thenReturn("Patient not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.getPatientById(1L));

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    void getAllPatient() {
        when(repository.findAll()).thenReturn(List.of(getPatient()));
        when(mapper.toPatientResponseList(any())).thenReturn(List.of(getPatientResponse()));

        List<PatientResponse> list = service.getAllPatient();

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void updatePatientOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getPatient()));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(getPatient())); // same patient
        when(repository.findByTelephone(anyString())).thenReturn(Optional.of(getPatient())); // same patient
        when(repository.save(any())).thenReturn(getPatient());
        when(mapper.toPatientResponse(any())).thenReturn(getPatientResponse());

        PatientResponse response = service.updatePatient(getPatientRequest());

        assertNotNull(response);
        assertEquals("patient@example.com", response.getEmail());
    }

    @Test
    void updatePatientKO_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("patient.notfound"), any(), any(Locale.class)))
                .thenReturn("Patient not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.updatePatient(getPatientRequest()));

        assertEquals("Patient not found", exception.getMessage());
    }

    @Test
    void updatePatientKO_EmailExists() {
        Patient existing = getPatient();
        existing.setId(2L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(getPatient()));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(existing));
        when(messageSource.getMessage(eq("email.exists"), any(), any(Locale.class)))
                .thenReturn("Email already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> service.updatePatient(getPatientRequest()));

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void updatePatientKO_TelephoneExists() {
        Patient existing = getPatient();
        existing.setId(2L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(getPatient()));
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(getPatient())); // same id
        when(repository.findByTelephone(anyString())).thenReturn(Optional.of(existing));
        when(messageSource.getMessage(eq("telephone.exists"), any(), any(Locale.class)))
                .thenReturn("Telephone already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> service.updatePatient(getPatientRequest()));

        assertEquals("Telephone already exists", exception.getMessage());
    }

    @Test
    void deletePatientByIdOK() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getPatient()));

        service.deletePatientById(1L);

        verify(repository, times(1)).delete(any());
    }

    @Test
    void deletePatientByIdKO() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("patient.notfound"), any(), any(Locale.class)))
                .thenReturn("Patient not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> service.deletePatientById(1L));

        assertEquals("Patient not found", exception.getMessage());
    }

    // Méthodes utilitaires
    private PatientRequest getPatientRequest() {
        PatientRequest request = new PatientRequest();
        request.setId(1L);
        request.setNom("Doe");
        request.setPrenom("John");
        request.setDateNaissance(LocalDate.of(1980, 1, 1));
        request.setSexe("M");
        request.setAdresse("123 Main St");
        request.setTelephone("0102030405");
        request.setEmail("patient@example.com");
        return request;
    }

    private Patient getPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNom("Doe");
        patient.setPrenom("John");
        patient.setDateNaissance(LocalDate.of(1980, 1, 1));
        patient.setSexe("M");
        patient.setAdresse("123 Main St");
        patient.setTelephone("0102030405");
        patient.setEmail("patient@example.com");
        return patient;
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