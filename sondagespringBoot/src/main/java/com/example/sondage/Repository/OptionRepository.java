package com.example.sondage.Repository;

import com.example.sondage.entity.Option;
import com.example.sondage.entity.Question;
import com.example.sondage.entity.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option,Long> {
    List<Option> findByQuestion(Question question);

    List<Option> findByQuestion_IdQuestion(Long idQuestion);

}
