package com.example.sondage.Repository;

import com.example.sondage.entity.Reponse;
import com.example.sondage.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse,Long> {
}
