package com.senac.rpgsaude.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ApkController {

    // Esta rota serve o arquivo APK
    @GetMapping("/download/app")
    public ResponseEntity<Resource> downloadApk() throws MalformedURLException {
        // O Dockerfile copia o APK para este caminho exato:
        Path path = Paths.get("/app/apk/rpgsaude.apk");
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rpgsaude.apk\"")
                .contentType(MediaType.parseMediaType("application/vnd.android.package-archive"))
                .body(resource);
    }
}