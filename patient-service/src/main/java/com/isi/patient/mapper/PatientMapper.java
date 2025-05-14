package com.isi.patient.mapper;


import com.isi.patient.dto.PatientRequest;
import com.isi.patient.dto.PatientResponse;
import com.isi.patient.model.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PatientMapper {

    Patient toPatient (PatientRequest request);
    PatientResponse toPatientResponse(Patient patient);
    List<PatientResponse> toPatientResponseList(List<Patient> patients);
}
