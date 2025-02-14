package a.board.root;

import a.board.question.QRepository;
import a.board.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RootController {

  private final QRepository qRepository;

  @GetMapping("/")
  public String index() {
    return "redirect:/question/list";
  }

  @GetMapping("/board")
  public String boardMain() {
    return "question_list";
  }
}
