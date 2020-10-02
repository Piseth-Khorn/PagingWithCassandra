package com.example.ManagementSystem.repositories;

import com.example.ManagementSystem.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {
    @Query("select count(*) from user")
    public List<User> getPaginator();
}
