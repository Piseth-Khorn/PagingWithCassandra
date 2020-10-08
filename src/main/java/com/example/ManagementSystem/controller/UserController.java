package com.example.ManagementSystem.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.ManagementSystem.ClassExeption.DocumentStorageException;
import com.example.ManagementSystem.core.UploadFileResponse;
import com.example.ManagementSystem.model.User;
import com.example.ManagementSystem.service.DocumentService;
import com.example.ManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DocumentService documentService;
    @GetMapping
    public ResponseEntity<Slice<User>>getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> findByID(@PathVariable("id")UUID id){
        Optional<User> user = userService.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Map<String,Object>> Create(@RequestParam(value = "file",required = false) MultipartFile file,
                                       @RequestParam("firstName") String firstname,
                                       @RequestParam("lastName") String lastname,
                                       @RequestParam("email") String email,
                                       @RequestParam("gender") String gender,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       @RequestParam("password") String password,
                                       @RequestParam("phoneNumber") String phoneNumber,
                                       @RequestParam("role") UUID role,
                                       @RequestParam("dateOfBirth") Date dateOfBirth,
                                       @RequestParam("address1") String address1,
                                       @RequestParam("address2") String address2,
                                       @RequestParam("city") String city,
                                       @RequestParam("state") String state,
                                       @RequestParam("zipcode") String zipcode) {
        try{
        int i =0;
        Map<String, Object> result = new HashMap<String, Object>();
        List<User>userList = new ArrayList<>();
        List<UploadFileResponse>uploadFileResponses = new ArrayList<>();
        while(i<2000) {
            User user= new User();
            user.setId(Uuids.timeBased());
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setEmail(email);
            user.setPassword(password);
            user.setConfirmPassword(confirmPassword);
            user.setPhoneNumber(phoneNumber);
            user.setRoleID(role);
            user.setDateOfBirth(dateOfBirth);
            user.setAddress1(address1);
            user.setAddress2(address2);
            user.setCity(city);
            user.setState(state);
            user.setZipcode(zipcode);
            user.setGender(gender);
            User user1 = userService.save(user);
            Map filename = documentService.storeFile(file, user);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/file/downloadFile/")
                    .path(filename.get("filename").toString())
                    .toUriString();
            UploadFileResponse uploadFileResponse = new UploadFileResponse(filename.get("filename").toString(), fileDownloadUri, file.getContentType(), file.getSize());
            userList.add(user1);
            uploadFileResponses.add(uploadFileResponse);
            i++;
        }
    result.put("File",uploadFileResponses);
    result.put("User",userList);
            System.out.println("UserController.Create");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }catch (Exception | DocumentStorageException e){
            e.fillInStackTrace();
            e.getMessage();
            System.out.println("e = " + e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable("id") UUID uuid ,@RequestParam(value = "file",required = false) MultipartFile file,
                                                    @RequestParam("firstName") String firstname,
                                                     @RequestParam("lastName") String lastname,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("gender") String gender,
                                                     @RequestParam("confirmPassword") String confirmPassword,
                                                     @RequestParam("password") String password,
                                                     @RequestParam("phoneNumber") String phoneNumber,
                                                     @RequestParam("role") UUID role,
                                                     @RequestParam("dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
                                                     @RequestParam("address1") String address1,
                                                     @RequestParam("address2") String address2,
                                                     @RequestParam("city") String city,
                                                     @RequestParam("state") String state,
                                                     @RequestParam("zipcode") String zipcode){
        System.out.println(uuid);

        try {
            if (userService.findById(uuid).isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            Optional<User> getUserObject = userService.findById(uuid);
            User user = getUserObject.get();
           user.setFirstName(firstname);
           user.setLastName(lastname);
           user.setEmail(email);
           user.setPhoneNumber(phoneNumber);
           user.setRoleID(role);
           user.setDateOfBirth(dateOfBirth);
           user.setAddress2(address2);
           user.setAddress1(address1);
           user.setCity(city);
           user.setState(state);
           user.setZipcode(zipcode);
           user.setGender(gender);
           if (!password.isEmpty()) user.setPassword(password);
           if (!confirmPassword.isEmpty()) user.setConfirmPassword(confirmPassword);
            Map<String,Object> result = new HashMap<String, Object>();
            result.put("User",userService.save(user));
            if (file!=null && !file.isEmpty()){
            Map filename =documentService.storeFile(file,user);
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("/file/downloadFile/")
                        .path(filename.get("filename").toString())
                        .toUriString();
                UploadFileResponse uploadFileResponse = new UploadFileResponse(filename.get("filename").toString(),fileDownloadUri,file.getContentType(),file.getSize());
            result.put("File",uploadFileResponse);
            }
            System.out.println("2");
            return new ResponseEntity<>(result,HttpStatus.CREATED);
        }catch (Exception | DocumentStorageException e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id){
        try {
            userService.destroy(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
