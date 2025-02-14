package a.board.question;

import a.board.answer.ARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QService {

  private final QRepository qRepository;

  public List<Question> getList() {
    return this.qRepository.findAll();
  }

  public Question getQuestion(Integer id) {
    Optional<Question> question = this.qRepository.findById(id);
    if(question.isPresent())
      return question.get();
    else
      throw new DataNotFoundException("question not found");
  }

  public void create(String subject, String content) {
    Question q = new Question();
    q.setSubject(subject);
    q.setContent(content);
    q.setCreateDate(LocalDateTime.now());

    this.qRepository.save(q);
  }
}
