package com.isi.rdv.mapper;


import com.isi.rdv.dto.RdvRequest;
import com.isi.rdv.dto.RdvResponse;
import com.isi.rdv.model.Rdv;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RdvMapper {

    Rdv toRdv (RdvRequest request);
    RdvResponse toRdvResponse(Rdv rdv);
    List<RdvResponse> toRdvResponseList(List<Rdv> rdvs);
}
