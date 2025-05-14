package com.isi.patient.service;

import com.isi.patient.dto.PatientRequest;
import com.isi.patient.dto.PatientResponse;

import java.util.List;

public interface PatientService {

    PatientResponse newPatient(PatientRequest request);
    PatientResponse getPatientById(Long id);
    List<PatientResponse> getAllPatient();
    PatientResponse updatePatient(PatientRequest request);
    void deletePatientById(Long id);
}
