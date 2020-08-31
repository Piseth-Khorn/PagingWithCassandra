package com.example.ManagementSystem.service;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.ManagementSystem.ClassExeption.DocumentStorageException;
import com.example.ManagementSystem.model.Document;
import com.example.ManagementSystem.model.User;
import com.example.ManagementSystem.repositories.DocumentRepository;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentService {
    private final Path fileStorageLocation;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    public DocumentService(Document fileStorage) throws DocumentException {
       this.fileStorageLocation  = Paths.get(fileStorage.getUploadDir())
                .toAbsolutePath().normalize();
    try {
        Files.createDirectories(fileStorageLocation);
    }catch (IOException ex){
        throw new DocumentException("Could not create the directory where the uploaded files will be stored.", ex);
    }
    }
    public Map<String,Object> storeFile(MultipartFile file, User user) throws DocumentStorageException {
    Map<String,Object> result = new HashMap<>();
    //Normalize file name
    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
    String fileName ="";
    try {
    // Check if the file's name contains invalid characters
    if(originalFileName.contains("..")){
        throw new DocumentStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
    }
    String fileExtension ="";
    try {
    fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
    }catch (Exception e){
    fileExtension = "";
    }
    fileName =new Date()+"_"+ user.getId()+"_"+user.getFirstname()+"_"+user.getLastname() +fileExtension;
    // Copy file to the target location (Replacing existing file with the same name)
    Path targetLocation = this.fileStorageLocation.resolve(fileName);
    Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
    Document document = documentRepository.checkDocumentById(user);
    if (document != null){
        document.setDocumentFormat(file.getContentType());
        document.setFileName(fileName);
        result.put("doc",documentRepository.save(document));
    }else{
        Document newDoc = new Document();
        newDoc.setDocumentId(Uuids.timeBased());
        newDoc.setFileName(fileName);
        newDoc.setDocumentFormat(file.getContentType());
        newDoc.setDocumentType(user.getFile());
        result.put("doc",documentRepository.save(newDoc));
    }
    result.put("fileName",fileName);
    return result;
    }catch ( IOException | DocumentStorageException ex ){
        throw new DocumentStorageException("Could not store file " + fileName + ". Please try again!", ex);
    }
    }
    public Resource loadFileResource(String filename) throws Exception {
        try{
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            }else {
            throw new FileNotFoundException("File not found"+filename);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + filename);
        }
    }
    public String getDocumentName(UUID userId) {

        return documentRepository.getUploadDocumentPath(userId);

    }

}
