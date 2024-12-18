package com.malinouski.multitenant_app.controller;

import com.malinouski.multitenant_app.exception.NotAuthorizedException;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "")
public class MainController {
    @GetMapping(path = "")
    public ResponseEntity<String> readAll(@AuthenticationPrincipal Token token) {
        if (!token.getAuthorities().contains(new SimpleGrantedAuthority("Display"))) {
            throw new NotAuthorizedException("This operation requires \"Display\" scope");
        }

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
