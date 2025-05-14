package com.isi.patient.exception;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
