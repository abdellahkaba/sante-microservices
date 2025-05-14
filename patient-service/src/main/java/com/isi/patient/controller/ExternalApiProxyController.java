package com.isi.patient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/proxy")
public class ExternalApiProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/model_settings")
    public ResponseEntity<String> proxyModelSettings(@RequestBody String body) {
        String externalUrl = "https://extensions.aitopia.ai/ai/model_settings";
        return restTemplate.postForEntity(externalUrl, body, String.class);
    }

    @GetMapping("/lang/fr")
    public ResponseEntity<String> proxyLangFr() {
        String externalUrl = "https://extensions.aitopia.ai/languages/lang/get/lang/fr";
        return restTemplate.getForEntity(externalUrl, String.class);
    }
}
