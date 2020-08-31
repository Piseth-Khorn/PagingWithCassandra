package com.example.ManagementSystem.service;

import com.example.ManagementSystem.model.Role;
import com.example.ManagementSystem.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public List<Role>findAll(){
        return roleRepository.findAll();
    }
    public Optional<Role> findById(UUID uuid){
        return roleRepository.findById(uuid);
    }
    public Role save(Role role){
        return roleRepository.save(role);
    }
    public void destroy(UUID uuid){
        roleRepository.deleteById(uuid);
    }
}
