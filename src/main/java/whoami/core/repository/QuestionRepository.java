package whoami.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whoami.core.domain.QuestionDomain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionDomain, Long> {
    public QuestionDomain findByQuestionId(Long question_id);
    public List<QuestionDomain> findByQuestionDateBetween(LocalDate from, LocalDate to);
}
