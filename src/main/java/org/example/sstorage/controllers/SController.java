package org.example.sstorage.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SController {
    @GetMapping("/")
    public String helloPage() {
        return "Hello!";
    }
}
