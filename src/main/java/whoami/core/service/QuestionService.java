package whoami.core.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import whoami.core.domain.question.Question;
import whoami.core.domain.question.QuestionRepository;
import whoami.core.dto.question.QuestionResponseDto;
import whoami.core.dto.question.QuestionSaveRequestDto;
import whoami.core.dto.question.QuestionUpdateRequestDto;
import whoami.core.exception.CustomMessage;
import whoami.core.exception.RecordNotFoundException;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    public Page<QuestionResponseDto> findAll(Pageable pageable){
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions.map(this::mapQuestionDomainToResponseDto);
    }

    public QuestionResponseDto findByQuestionId(Long questionId){
        if (questionRepository.existsById(questionId)) {
            Question question = questionRepository.findByQuestionId(questionId);
            return modelMapper.map(question,QuestionResponseDto.class);
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + questionId);
        }
    }

    public void deleteByQuestionId(Long questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
            return;
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + questionId);
        }
    }

    public QuestionResponseDto createQuestion(QuestionSaveRequestDto questionSaveRequestDto) {
        try {
            questionSaveRequestDto.printAll();
            Question question = modelMapper.map(questionSaveRequestDto, Question.class);
            question = questionRepository.save(question);
            return modelMapper.map(question,QuestionResponseDto.class);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getCause().getCause().getMessage());
        }
    }

    public QuestionResponseDto updateQuestion(QuestionUpdateRequestDto questionUpdateRequestDto) {
        if (questionRepository.existsById(questionUpdateRequestDto.getQuestionId())) {
            Question question = modelMapper.map(questionUpdateRequestDto, Question.class);
            question = questionRepository.save(question);
            return modelMapper.map(question, QuestionResponseDto.class);
        } else {
            throw new RecordNotFoundException(CustomMessage.DOESNOT_EXIT + questionUpdateRequestDto.getQuestionId());
        }
    }

    private QuestionResponseDto mapQuestionDomainToResponseDto(Question question) {
        return modelMapper.map(question,QuestionResponseDto.class);
    }
}