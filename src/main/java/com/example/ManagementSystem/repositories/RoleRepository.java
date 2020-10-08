package com.example.ManagementSystem.repositories;

import com.example.ManagementSystem.model.Role;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends CassandraRepository<Role, UUID> {
    @Query("SELECT * FROM role WHERE name = ?1 ALLOW FILTERING")
    public List<Role> getALl(String name);
    @Query("SELECT * FROM role ")
    public List<Role> getAllByOrderBy(String orderBy);
    @Query("select * from role limit ?0")
    public List<Role> getDefaultPage(int limit);
    @Query("select * from role where token(id) > token(?0) limit ?1")
    public List<Role> nextPage(UUID uuid, int limit);
    @Query("select count(1) from role")
    public Long countRole();
    @Query("select * from role where token(id) < token(?0)")
    public List<Role> previousPage(UUID uuid);
}
