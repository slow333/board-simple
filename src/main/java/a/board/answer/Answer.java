package a.board.answer;

import a.board.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

  @ManyToOne
  Question question;
}
