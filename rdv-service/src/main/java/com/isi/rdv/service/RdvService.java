package com.isi.rdv.service;


import com.isi.rdv.dto.RdvRequest;
import com.isi.rdv.dto.RdvResponse;

import java.util.List;

public interface RdvService {

    RdvResponse newRdv(RdvRequest request);
    RdvResponse getRdvById(Long id);
    List<RdvResponse> getAllRdv();
    RdvResponse updateRdv(RdvRequest request);
    void deleteRdvById(Long id);
}
