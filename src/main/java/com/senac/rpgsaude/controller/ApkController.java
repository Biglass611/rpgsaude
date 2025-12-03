package com.senac.rpgsaude.controller;

import org.springframework.core.io.ClassPathResource; // IMPORTANTE: Mudei o import
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // Use Controller em vez de RestController para páginas
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody; // Necessário para o download

import java.io.IOException;

@Controller // Mudei de @RestController para @Controller para o index.html funcionar
public class ApkController {

    // Serve a página inicial
    @GetMapping(value = {"/", "/index.html", "/rgpsaude"})
    public String home() {
        return "index"; // Vai procurar o index.html na pasta templates ou static
    }

    // Serve o Download
    @GetMapping(value = {"/download/app", "/rgpsaude/download/app"})
    @ResponseBody // Indica que o retorno é o arquivo, não uma página
    public ResponseEntity<Resource> downloadApk() throws IOException {

        // CORREÇÃO: Busca o arquivo dentro da pasta 'static' do projeto
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