package com.example.sondage.Repository;

import com.example.sondage.entity.Question;
import com.example.sondage.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    List<Question> findBySondage_IdSondage(Long idSondage);
}
