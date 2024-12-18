package com.malinouski.multitenant_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "")
public class MainController {
    @GetMapping(path = "")
    public ResponseEntity<String> getDroneMedications() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
