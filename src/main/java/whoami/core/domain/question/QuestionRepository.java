package whoami.core.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    public Question findByQuestionId(Long question_id);
    public void deleteById(Long question_id);
}

