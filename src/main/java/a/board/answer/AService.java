package a.board.answer;

import a.board.question.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
