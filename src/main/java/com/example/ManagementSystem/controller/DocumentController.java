package com.example.ManagementSystem.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.ManagementSystem.ClassExeption.DocumentStorageException;
import com.example.ManagementSystem.core.UploadFileResponse;
import com.example.ManagementSystem.model.User;
import com.example.ManagementSystem.repositories.DocumentRepository;
import com.example.ManagementSystem.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping("/file")
public class DocumentController {
    @Autowired
    private final DocumentService documentService;
    @Autowired
    private DocumentRepository documentRepository;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }
    @PostMapping
    public UploadFileResponse uploadFile(@RequestParam("name") String name,@RequestParam("file") MultipartFile file) throws DocumentStorageException {
        User user = new User();
        user.setFirstName(name);
        user.setLastName("Khorn");
        user.setId(Uuids.timeBased());

    Map fileName = documentService.storeFile(file,user);
    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/downloadFile/")
                .path(fileName.get("filename").toString())
                .toUriString();
   return new UploadFileResponse(fileName.get("filename").toString(),fileDownloadUri,file.getContentType(), file.getSize());

    }
    @GetMapping("/getDoc/{id}")
    public ResponseEntity<Resource> getUrlImage(@PathVariable("id") UUID uuid){
    String getDoc = documentRepository.getUploadDocumentPath(uuid);
    Resource resource = null;
        if (getDoc != null && !getDoc.isEmpty()){
            try {
                resource = documentService.loadFileResource(getDoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String filename = resource != null ? resource.getFilename() : null;
            assert filename != null;
            String getExtendSion = filename.split("[.]")[1];
            System.out.println(getExtendSion);
            String contentType = null;
            switch (getExtendSion){
                case "pdf": contentType = "application/pdf";
                    break;
                case "png":contentType ="image/png";
                    break;
                case "jpeg":
                case "jpg":
                    contentType = "image/jpeg";
                    break;
                default: contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") UUID uuid ,HttpServletRequest request) throws IOException {
    String  getDoc= documentService.getDocumentName(uuid);
    Resource resource = null;
    if (getDoc != null && !getDoc.isEmpty()){
    try {
    resource = documentService.loadFileResource(getDoc);
    }catch (Exception e) {
        e.printStackTrace();
    }
    String content = null;
    content = request.getServletContext().getMimeType(resource != null ? resource.getFile().getAbsolutePath() : null);
    if (content ==null) content = "application/octet-stream";
    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(content))
            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ (resource != null ? resource.getFilename() : null) +"\"")
            .body(resource);
    }else{
    return ResponseEntity.notFound().build();
        }
    }
}
