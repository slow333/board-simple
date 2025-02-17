package a.board.answer;

import a.board.question.QService;
import a.board.question.Question;
import a.board.user.SiteUser;
import a.board.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AController {

  private final AService aService;
  private final QService qService;
  private final UserService userService;

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create/{id}")
  public String create(Model model,
                       @PathVariable("id") Integer id,
                       @Valid AnswerForm answerForm, BindingResult bindingResult,
                       Principal principal) {
    Question question = this.qService.getQuestion(id);
    SiteUser siteUser = this.userService.getUser(principal.getName());

    if (bindingResult.hasErrors()) {
      model.addAttribute("question", question);
      return "question_detail";
    }
    Answer answer = this.aService.create(question, answerForm.getContent(), siteUser);
    return String.format("redirect:/question/detail/%s#answer_%s", id, answer.getId());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/modify/{id}")
  public String modify(@PathVariable("id") Integer id,
                       Principal principal,
                       AnswerForm answerForm) {
    Answer answer = this.aService.getAnswer(id);
    if (!answer.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
    }
    answerForm.setContent(answer.getContent());
    return "answer_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String modify(@PathVariable("id") Integer id,
                       Principal principal,
                       @Valid AnswerForm answerForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "answer_form";
    }
    Answer answer = this.aService.getAnswer(id);
    if (!answer.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
    }
    this.aService.modify(answer, answerForm.getContent());
    return String.format("redirect:/question/detail/%s#answer_%s",
            answer.getQuestion().getId(), answer.getId());
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/delete/{id}")
  public String delete(@PathVariable("id") Integer id,
                       Principal principal) {
    Answer answer = aService.getAnswer(id);
    if (!answer.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
    }
    this.aService.delete(answer);
    return String.format("redirect:/question/detail/%s#answer_%s",
            answer.getQuestion().getId(), answer.getId());  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/vote/{id}")
  public String vote(@PathVariable("id") Integer id,
                     Principal principal) {
    Answer answer = aService.getAnswer(id);
    SiteUser siteUser = userService.getUser(principal.getName());
    aService.vote(answer, siteUser);
    return String.format("redirect:/question/detail/%s#answer_%s",
            answer.getQuestion().getId(), answer.getId());
  }
}
