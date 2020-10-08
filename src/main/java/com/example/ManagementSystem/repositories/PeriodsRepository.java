package com.example.ManagementSystem.repositories;

import com.example.ManagementSystem.model.Periods;
import com.example.ManagementSystem.model.Role;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PeriodsRepository extends CassandraRepository<Periods, Integer> {
    @Query("select * from periods where token(id) < token(?0)")
   public List<Periods> page(int uuid);
    @Query("select * from periods limit ?0")
    public List<Periods> defaultPage(int limit);
    @Query("select * from periods where token(id) > token(?0) limit ?1")
    public List<Periods> getPage(UUID uuid, int limit);
}
