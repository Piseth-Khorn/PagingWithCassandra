package com.example.ManagementSystem.repositories;

import com.example.ManagementSystem.model.Document;
import com.example.ManagementSystem.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends CassandraRepository<Document, UUID> {
@Query("SELECT * FROM document WHERE userid = ?0")
    Document checkDocumentById(User userid);
@Query("SELECT * FROM document WHERE userid = ?0")
    String getUploadDocumentPath(UUID uuid);

}
