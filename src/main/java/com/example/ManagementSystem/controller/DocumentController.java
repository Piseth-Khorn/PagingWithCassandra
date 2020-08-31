package com.example.ManagementSystem.controller;

import com.example.ManagementSystem.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/fileapi")
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getUrlImage(@PathVariable("id")UUID uuid){
    String getDoc = documentService.getDocumentName(uuid);
    Resource resource = null;
        if (getDoc != null && !getDoc.isEmpty()){
            try {
                resource = documentService.loadFileResource(getDoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String filename = resource.getFilename();
            String getExtendSion = filename.split("[.]")[1];
            String contentType = null;
            switch (getExtendSion){
                case "pdf": contentType = "application/pdf";
                    break;
                case "png":contentType ="image/png";
                    break;
                case "jpeg": contentType = "image/jpeg";
                    break;
                default: contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
