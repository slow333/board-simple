package a.board.answer;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

  @NotEmpty(message = "내용은 필수입니다.")
  private String content;
}
