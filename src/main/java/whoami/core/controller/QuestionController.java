package whoami.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whoami.core.dto.QuestionDto;
import whoami.core.service.QuestionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<QuestionDto>> getQuestion(
            @RequestParam(value="startDate") LocalDate startDate,
            @RequestParam(value="endDate") LocalDate endDate) {
        List<QuestionDto> questionList = questionService.findByCreateDateBetween(startDate, endDate);
        return new ResponseEntity<List<QuestionDto>>(questionList, HttpStatus.OK);
    }

    @GetMapping(value = "/{questionId}")
    public ResponseEntity<QuestionDto> getQuestion(@PathVariable Long questionId) {
        QuestionDto question = questionService.findByQuestionId(questionId);
        return new ResponseEntity<QuestionDto>(question, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto questionDto) {
        QuestionDto question = questionService.createQuestion(questionDto);
        return new ResponseEntity<QuestionDto>(question, HttpStatus.OK);
    }
}
