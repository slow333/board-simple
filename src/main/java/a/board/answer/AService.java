package a.board.answer;

import a.board.question.DataNotFoundException;
import a.board.question.Question;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AService {

  private final ARepository aRepository;

  public Answer getAnswer(Integer id) {
    Optional<Answer> answer = this.aRepository.findById(id);
    if(answer.isPresent()){
      return answer.get();
    } else {
      throw new DataNotFoundException("!!!!!!!! NO ANSWER !!!!!!!");
    }
  }

  public void create(Answer answer) {
    this.aRepository.save(answer);
  }

  public void create(Question question, String content) {
    Answer answer = new Answer();
    answer.setQuestion(question);
    answer.setContent(content);
    answer.setCreateDate(LocalDateTime.now());
    this.aRepository.save(answer);
  }
}
