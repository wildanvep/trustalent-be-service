package com.evact.trustalent.controller;

import com.evact.trustalent.common.dto.ResponseDTO;
import com.evact.trustalent.common.utils.ResponseUtil;
import com.evact.trustalent.config.AuthenticationService;
import com.evact.trustalent.dto.request.AuthenticationRequest;
import com.evact.trustalent.dto.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseUtil.generateResponseSuccess(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDTO<AuthenticationResponse>> refreshToken(@NonNull HttpServletRequest request) {
        return ResponseUtil.generateResponseSuccess(service.refreshToken(request.getHeader("Authorization")));
    }

}
