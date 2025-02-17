package a.board.answer;

import a.board.question.Question;
import a.board.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(columnDefinition = "TEXT")
  String content;

  LocalDateTime createDate;
  LocalDateTime modifyDate;

  @ManyToOne
  Question question;

  @ManyToOne
  SiteUser author;

  @ManyToMany
  Set<SiteUser> voter;
}
