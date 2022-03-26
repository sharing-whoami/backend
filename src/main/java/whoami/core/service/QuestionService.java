package whoami.core.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import whoami.core.domain.QuestionDomain;
import whoami.core.dto.QuestionDto;
import whoami.core.exception.CustomMessage;
import whoami.core.exception.RecordNotFoundException;
import whoami.core.repository.QuestionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public List<QuestionDto> findByCreateDateBetween(LocalDate startDate, LocalDate endDate){
        List<QuestionDomain> questionDomainList =
                questionRepository.findByQuestionDateBetween(startDate, endDate);
        return questionDomainList.stream()
                .map(this::copyQuestionDomainToDto)
                .collect(Collectors.toList());
    }

    public QuestionDto findByQuestionId(Long questionId){
        if (questionRepository.existsById(questionId)) {
            QuestionDomain questionDomain = questionRepository.findByQuestionId(questionId);
            return copyQuestionDomainToDto(questionDomain);
        } else {
            // TODO: Throw some exception
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + questionId);
        }
    }

    public QuestionDto createQuestion(QuestionDto questionDto) {
        try {
            QuestionDomain questionDomain = copyQuestionDtoToDomain(questionDto);
            questionDomain = questionRepository.save(questionDomain);
            return copyQuestionDomainToDto(questionDomain);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getCause().getCause().getMessage());
        }
    }

    private QuestionDto copyQuestionDomainToDto(QuestionDomain questionDomain) {
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(questionDomain, questionDto);
        return questionDto;
    }
    private QuestionDomain copyQuestionDtoToDomain(QuestionDto questionDto) {
        QuestionDomain questionDomain = new QuestionDomain();
        BeanUtils.copyProperties(questionDto, questionDomain);
        return questionDomain;
    }
}
