package a.board.question;

import a.board.answer.Answer;
import a.board.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(length = 200)
  String subject;

  @Column(columnDefinition = "TEXT")
  String content;

  LocalDateTime createDate;
  LocalDateTime modifyDate;

  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
  List<Answer> answerList;

  @ManyToOne
  SiteUser author;

  @ManyToMany
  Set<SiteUser> voter;
}
