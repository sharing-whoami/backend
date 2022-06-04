package whoami.core.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import whoami.core.dto.question.QuestionResponseDto;
import whoami.core.dto.question.QuestionSaveRequestDto;
import whoami.core.dto.question.QuestionUpdateRequestDto;
import whoami.core.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = "/{questionId}")
    public QuestionResponseDto getQuestion(@PathVariable Long questionId) {
        return questionService.findByQuestionId(questionId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity getQuestionList(
            @PageableDefault(size=5) Pageable pageable){
        Page<QuestionResponseDto> questionResponseDtos= questionService.findAll(pageable);
        return ResponseEntity.ok().body(questionResponseDtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteByQuestionId(questionId);
        return ResponseEntity.ok().body("Success");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity createQuestion(@RequestBody QuestionSaveRequestDto questionSaveRequestDto) {
        QuestionResponseDto questionResponseDto = questionService.createQuestion(questionSaveRequestDto);
        return ResponseEntity.ok().body(questionResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "")
    public ResponseEntity updateQuestion(@RequestBody QuestionUpdateRequestDto questionUpdateRequestDto) {
        QuestionResponseDto questionResponseDto = questionService.updateQuestion(questionUpdateRequestDto);
        return ResponseEntity.ok().body(questionResponseDto);
    }
}
