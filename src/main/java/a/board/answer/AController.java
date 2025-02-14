package a.board.answer;

import a.board.question.QService;
import a.board.question.Question;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AController {

  private final AService aService;
  private final QService qService;

  @PostMapping("/create/{id}")
  public String create(Model model,
                       @PathVariable("id") Integer id,
                       @Valid AnswerForm answerForm, BindingResult bindingResult) {
    Question question = this.qService.getQuestion(id);

    if (bindingResult.hasErrors()) {
      model.addAttribute("question", question);
      return "question_detail";
    }

    this.aService.create(question, answerForm.getContent());

    return String.format("redirect:/question/detail/%s", id);
  }
}
