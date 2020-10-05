package com.example.ManagementSystem.service;

import com.example.ManagementSystem.model.Role;
import com.example.ManagementSystem.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    public Slice<Role> Paging(){
        return roleRepository.findAll(CassandraPageRequest.of(0,10));
    }
    public List<Role> findAll() {
        return roleRepository.findAll();

    }

    public Optional<Role> findById(UUID uuid) {
        return roleRepository.findById(uuid);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void destroy(UUID uuid) {
        roleRepository.deleteById(uuid);
    }
    public List<Role> pagination(String uuid, int limit){
        if (uuid.isEmpty())return roleRepository.getDefaultPage(limit);
        return roleRepository.getPage(UUID.fromString(uuid),limit);
    }
    public Long countRole(){
        return roleRepository.countRole();
    }
}
