package a.board.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QController {

  private final QService qService;

  @GetMapping("/list")
  public String list(Model model) {
    List<Question> questions = this.qService.getList();
    model.addAttribute("questions", questions);
    return "question_list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id) {
    Question question = this.qService.getQuestion(id);
    model.addAttribute("question", question);
    return "question_detail";
  }

  @GetMapping("/create")
  public String createQuestion(){
    return "question_form";
  }

  @PostMapping("/create")
  public String createsQuestion(@RequestParam("subject") String subject,
                                @RequestParam("content") String content){
    this.qService.create(subject, content);
    return "redirect:/question/list";
  }
}
