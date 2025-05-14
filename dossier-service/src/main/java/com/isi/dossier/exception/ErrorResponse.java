package com.isi.dossier.exception;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
