package com.example.ManagementSystem.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.ManagementSystem.model.Role;
import com.example.ManagementSystem.service.RoleService;
import org.apache.commons.lang.RandomStringUtils;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @GetMapping("/page/{d}")
    public ResponseEntity<Slice<Role>> paging(){
        return new ResponseEntity<>(roleService.Paging(),HttpStatus.ACCEPTED);
    }
    @PostMapping("/d")
    public ResponseEntity<Role> save(@RequestParam("name") String name){
        Role role = new Role();
        role.setId(Uuids.timeBased());
        role.setName(name);
        return new ResponseEntity<>(roleService.save(role),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<List<Role>> save(@RequestBody Role role){
        try {
            int i = 1;
            List<Role>role1 = new ArrayList<>();
            int lenth= 10;
            while (i<2000) {
                String genderatedString = RandomStringUtils.random(lenth, true, true);
                role.setId(Uuids.timeBased());
                role.setName(genderatedString);
                 role1.add(roleService.save(role));
                i++;
            }
            return new ResponseEntity<>(role1,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable("id") UUID uuid,@RequestBody Role role){
        Optional<Role> role1 = roleService.findById(uuid);
        if (role1.isPresent()){
            role.setId(role1.get().getId());
            return new ResponseEntity<>(roleService.save(role),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") UUID uuid) {
        try {
            roleService.destroy(uuid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/page")
    public ResponseEntity<Map<String,Object>> getPagination(@RequestParam(value = "pageSize",required = false) int limit,@RequestParam(value = "pageNumber",required = false) String uuid){
        System.out.println(uuid+" "+limit);
        HashMap<String,Object> map = new HashMap<>();
        map.put("payload",roleService.pagination(uuid,limit));
    return new ResponseEntity<>(map,HttpStatus.ACCEPTED);
    }
    @GetMapping("/count")
    public ResponseEntity<Map<String,Object>> roleCount(){
        Map<String,Object> map = new HashMap<>();
        map.put("rowCount",roleService.countRole());
        return new ResponseEntity<>(map,HttpStatus.ACCEPTED);
    }
}
