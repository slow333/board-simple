package a.board.question;

import a.board.answer.AService;
import a.board.answer.AnswerForm;
import a.board.user.SiteUser;
import a.board.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QController {

  private final QService qService;
  private final UserService userService;

  @GetMapping(value = "/list")
  public String list(Model model,
                     @RequestParam(value = "page", defaultValue = "0") int page,
                     @RequestParam(value ="kw", defaultValue = "") String kw) {
    Page<Question> paging = this.qService.getList(page, kw);
    model.addAttribute("paging", paging);
    model.addAttribute("kw", kw);
    return "question_list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id,
                       AnswerForm answerForm) {
    Question question = this.qService.getQuestion(id);
    model.addAttribute("question", question);
    return "question_detail";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/create")
  public String questionCreate(QuestionForm questionForm) {
    return "question_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create")
  public String questionCreate(@Valid QuestionForm questionForm,
                               BindingResult bindingResult,
                               Principal principal) {
    SiteUser siteUser = userService.getUser(principal.getName());
    if (bindingResult.hasErrors()) {
      return "question_form";
    }
    this.qService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
    return "redirect:/question/list";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/modify/{id}")
  public String modify(QuestionForm questionForm,
                       @PathVariable("id") Integer id,
                       Principal principal) {
    Question question = this.qService.getQuestion(id);
    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }
    questionForm.setSubject(question.getSubject());
    questionForm.setContent(question.getContent());

    return "question_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String modify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                       Principal principal,
                       @PathVariable("id") Integer id){
    if (bindingResult.hasErrors()) {
      return "question_form";
    }
    Question question = this.qService.getQuestion(id);
    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }
    this.qService.modify(question, questionForm.getSubject(), questionForm.getContent());
    return String.format("redirect:/question/detail/%s", id);
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String delete(Principal principal,
                       @PathVariable("id") Integer id) {
    Question question = this.qService.getQuestion(id);
    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
    }
    this.qService.delete(question);
    return "redirect:/";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/vote/{id}")
  public String vote(Principal principal, @PathVariable("id") Integer id) {
    Question question = qService.getQuestion(id);
    SiteUser siteUser = userService.getUser(principal.getName());
    this.qService.vote(question, siteUser);
    return String.format("redirect:/question/detail/%s", id);
  }

}
