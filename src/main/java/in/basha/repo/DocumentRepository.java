package in.basha.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.basha.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
}

