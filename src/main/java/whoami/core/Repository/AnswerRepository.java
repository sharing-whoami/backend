package whoami.core.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import whoami.core.Domain.Answer;

import java.util.List;


public interface AnswerRepository extends JpaRepository<Answer, Long>{

//    @Query("SELECT a FROM Answers a ORDER BY a.id DESC")
    List<Answer> findAll();

}
