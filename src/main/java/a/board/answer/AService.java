package a.board.answer;

import a.board.question.DataNotFoundException;
import a.board.question.Question;
import a.board.user.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

  public Answer create(Question question, String content, SiteUser author) {
    Answer answer = new Answer();
    answer.setQuestion(question);
    answer.setContent(content);
    answer.setAuthor(author);
    answer.setCreateDate(LocalDateTime.now());
    this.aRepository.save(answer);
    return answer;
  }

  public void modify(Answer answer, String content) {
    answer.setContent(content);
    answer.setModifyDate(LocalDateTime.now());
    this.aRepository.save(answer);
  }

  public void delete(Answer answer) {
    aRepository.delete(answer);
  }

  public void vote(Answer answer, SiteUser siteUser) {
    answer.getVoter().add(siteUser);
    aRepository.save(answer);
  }
}
