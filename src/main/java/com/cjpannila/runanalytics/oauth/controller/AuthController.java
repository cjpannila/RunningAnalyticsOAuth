package com.cjpannila.runanalytics.oauth.controller;

import com.cjpannila.runanalytics.oauth.service.EmailService;
import com.cjpannila.runanalytics.oauth.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final EmailService emailService;

    public AuthController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Void> authenticate(@RequestParam String code,
                               @RequestParam(required = false) String scope) throws Exception {
        logger.info("Authenticating with code: " + code);
        emailService.sendAuthorizationCodeEmailViaResend(code, scope);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/runanalytics-oauth/"))
                .build();
    }

    @GetMapping(value = "/apiinfo")
    public Map<String, String> apiInfo() {
        logger.info("Request in: /apiinfo");
        Map<String, String> resultsMap = new HashMap<>();
        resultsMap.put("Application", Constants.APPLICATION_NAME);
        resultsMap.put("Version", Constants.APPLICATION_VERSION);
        return resultsMap;
    }
}
