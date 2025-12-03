package com.senac.rpgsaude.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApkController {

    // Garante que seu site abra com ou sem o nome do projeto na URL
    @GetMapping(value = { "/", "/index.html", "/rpgsaude", "/rpgsaude/" })
    public String home() {
        return "forward:/index.html";
    }
}