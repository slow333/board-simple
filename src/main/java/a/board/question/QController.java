package a.board.question;

import a.board.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QController {

  private final QService qService;

  @GetMapping(value="/list")
  public String list(Model model,
                     @RequestParam(value = "page", defaultValue = "0") int page) {
    Page<Question> paging = this.qService.getList(page);
    model.addAttribute("paging", paging);
    return "question_list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id,
                       AnswerForm answerForm) {
    Question question = this.qService.getQuestion(id);
    model.addAttribute("question", question);
    return "question_detail";
  }

  @GetMapping("/create")
  public String questionCreate(QuestionForm questionForm){
    return "question_form";
  }

  @PostMapping("/create")
  public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      return "question_form";
    }
    this.qService.create(questionForm.getSubject(), questionForm.getContent());
    return "redirect:/question/list";
  }
}
