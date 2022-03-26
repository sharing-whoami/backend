package whoami.core.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whoami.core.DTO.AnswerListResponseDTO;
import whoami.core.DTO.AnswerResponseDTO;
import whoami.core.DTO.AnswerSaveRequestDTO;
import whoami.core.DTO.AnswerUpdateRequestDTO;
import whoami.core.Domain.Answer;
import whoami.core.Repository.AnswerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    // 답글(Answer) 조회
    //// Id조회
    public AnswerResponseDTO findById(Long answerId){
        Answer entity = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 존재하지 않습니다. id="+answerId));
        return new AnswerResponseDTO(entity);
    }

    //// 전체조회
    @Transactional
    public List<AnswerListResponseDTO> findAllAnswer(){
        return answerRepository.findAll().stream()
                .map(AnswerListResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 답글(Answer) 작성
    @Transactional
    public Long save(AnswerSaveRequestDTO requestDTD){
        return answerRepository.save(requestDTD.toEntiy()).getAnswerId();
    }

    // 답글(Answer) 수정
    @Transactional
    public Long update (Long id, AnswerUpdateRequestDTO requestDTO){
        Answer answer = answerRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        answer.update(requestDTO.getAnswerId(), requestDTO.getAnswerContents(), requestDTO.getUpdatedAt());
        return id;
    }

    // 답글(Answer) 삭제
    @Transactional
    public void delete(Long id){
        Answer answer = answerRepository.findById(id).orElseThrow(()
        -> new IllegalArgumentException("해당 게시글이 없습니다. id = "+id));

        answerRepository.delete(answer);
    }
}
