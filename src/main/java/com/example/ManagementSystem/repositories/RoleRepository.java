package com.example.ManagementSystem.repositories;

import com.example.ManagementSystem.model.Role;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends CassandraRepository<Role, UUID> {
}
