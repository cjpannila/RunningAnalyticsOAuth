package com.cjpannila.runanalytics.oauth.controller;

import com.cjpannila.runanalytics.oauth.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/authenticate")
    public ResponseEntity<Void> authenticate(@RequestParam String code,
                               @RequestParam(required = false) String scope) throws IOException {

        saveCode(code);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/runanalytics-oauth/"))
                .build();
    }

    private synchronized void saveCode(String code) throws IOException {

        Path path = Paths.get("auth_codes.csv");

        Files.write(
                path,
                (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "," + code + System.lineSeparator()).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
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
