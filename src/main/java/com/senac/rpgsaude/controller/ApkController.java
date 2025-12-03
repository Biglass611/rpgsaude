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

    // ✅ Rota principal: Quando acessar "/rgpsaude", o Java entrega o index.html da pasta static
    @GetMapping(value = {"/", "/rgpsaude"})
    public String home() {
        // "forward" diz para o Spring: "Não renderize nada, só entregue o arquivo estático"
        return "forward:/index.html";
    }

    // ✅ Rota de Download: Mantivemos a correção anterior
    @GetMapping(value = {"/download/app", "/rgpsaude/download/app"})
    @ResponseBody
    public ResponseEntity<Resource> downloadApk() throws IOException {
        // Busca o arquivo dentro de src/main/resources/static/rpgsaude.apk
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