package com.devportalx.config;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping(path = "api/csrf-token")
@Hidden
public class CsrfController {

    @GetMapping
    public CsrfToken getCsrfToken(CsrfToken token) {
        return token;
    }
}

