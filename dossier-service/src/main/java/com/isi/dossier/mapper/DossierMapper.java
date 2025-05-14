package com.isi.dossier.mapper;


import com.isi.dossier.dto.DossierRequest;
import com.isi.dossier.dto.DossierResponse;
import com.isi.dossier.model.Dossier;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface DossierMapper {

    Dossier toDossier(DossierRequest request);
    DossierResponse toDossierResponse(Dossier response);
    List<DossierResponse> toDossierResponseList(List<Dossier> dossiers);
}
