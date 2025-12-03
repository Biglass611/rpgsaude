package com.senac.rpgsaude.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ApkController {

    // ✅ Mapeamos TODAS as formas que alguém pode tentar entrar no seu site
    @GetMapping(value = {
            "/",                        // Raiz (opcional)
            "/rpgsaude",                // O nome do projeto
            "/rpgsaude/",               // O nome do projeto com barra
            "/rpgsaude/index.html",     // O caminho completo que você pediu
            "/rgpsaude",                // (Previne erro de digitação comum 'rgp')
            "/rgpsaude/index.html"      // (Previne erro de digitação no html)
    })
    public String home() {
        // Isso força o Java a mostrar o arquivo que está em src/main/resources/static/index.html
        return "forward:/index.html";
    }

    // ✅ Rota de Download (também protegida com o prefixo)
    @GetMapping(value = {
            "/download/app",
            "/rpgsaude/download/app",       // Caminho correto
            "/rpgsaude/rpgsaude.apk"        // Caminho direto pro arquivo
    })
    @ResponseBody
    public ResponseEntity<Resource> downloadApk() throws IOException {
        Resource resource = new ClassPathResource("static/rpgsaude.apk");

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rpgsaude.apk\"")
                .contentType(MediaType.parseMediaType("application/vnd.android.package-archive"))
                .body(resource);
    }
}