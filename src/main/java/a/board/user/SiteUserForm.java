package a.board.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserForm {

  @NotEmpty(message = "사용자명은 필수입니다.")
  @Size(min = 3, max = 30)
  private String username;

  @NotEmpty(message = "암호는 필수 입니다.")
  private String password1;

  @NotEmpty(message = "암호확인은 필수 입니다.")
  private String password2;

  @NotEmpty(message = "이메일은 필수 입니다.")
  @Email
  private String email;
}
