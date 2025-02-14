package a.board.answer;

import a.board.question.QService;
import a.board.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AController {

  private final AService aService;
  private final QService qService;

  @PostMapping("/create/{id}")
  public String create(@PathVariable("id") Integer id,
                       @RequestParam("content") String content) {
    Question question = this.qService.getQuestion(id);

    Answer answer = new Answer();
    answer.setContent(content);
    answer.setCreateDate(LocalDateTime.now());
    answer.setQuestion(question);
    this.aService.create(answer);

    return String.format("redirect:/question/detail/%s", question.getId());
  }
}
