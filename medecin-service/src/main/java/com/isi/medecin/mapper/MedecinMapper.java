package com.isi.medecin.mapper;

import com.isi.medecin.dto.MedecinRequest;
import com.isi.medecin.dto.MedecinResponse;
import com.isi.medecin.model.Medecin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MedecinMapper {

    Medecin toMedecin (MedecinRequest request);
    MedecinResponse toMedecinResponse(Medecin medecin);
    List<MedecinResponse> toMedecinResponseList(List<Medecin> medecins);
}
