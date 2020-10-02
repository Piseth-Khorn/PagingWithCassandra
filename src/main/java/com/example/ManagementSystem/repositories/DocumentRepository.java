package com.example.ManagementSystem.repositories;

import com.example.ManagementSystem.model.Document;
import com.example.ManagementSystem.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends CassandraRepository<Document, UUID> {
@Query("SELECT * FROM document WHERE userid = ?0 ALLOW FILTERING")
  public Document checkDocumentById(UUID userid);
@Query("SELECT fileName FROM document WHERE userid = ?0 ALLOW FILTERING")
   public String getUploadDocumentPath(UUID userid);

}
