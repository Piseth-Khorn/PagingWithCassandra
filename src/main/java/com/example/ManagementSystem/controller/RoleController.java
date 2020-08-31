package com.example.ManagementSystem.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.ManagementSystem.model.Role;
import com.example.ManagementSystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping
    public ResponseEntity<List<Role>> getALl(){
        return ResponseEntity.ok(roleService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable("id") UUID uuid){
        Optional<Role>role = roleService.findById(uuid);
        return role.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PostMapping
    public ResponseEntity<Role> save(@RequestBody Role role){
        try {
            role.setId(Uuids.timeBased());
            Role role1 = roleService.save(role);
            return new ResponseEntity<>(role1,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Role> update(@PathVariable("id") UUID uuid,@RequestBody Role role){
        Optional<Role> role1 = roleService.findById(uuid);
        if (role1.isPresent()){
            role.setId(role1.get().getId());
            return new ResponseEntity<>(roleService.save(role),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") UUID uuid){
        try {
            roleService.destroy(uuid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
