package com.example.sondage.Repository;

import com.example.sondage.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SondageRepository extends JpaRepository<Sondage,Long> {
    List<Sondage> findByTitleContainingIgnoreCase(String title);
}
