package a.board.question;

import a.board.answer.Answer;
import a.board.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QService {

  private final QRepository qRepository;

  public List<Question> getList() {
    return this.qRepository.findAll();
  }

  public Page<Question> getList(int page, String kw) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createDate"));
    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    Specification<Question> spec = search(kw);
    return this.qRepository.findAll(spec, pageable);
  }

  public Question getQuestion(Integer id) {
    Optional<Question> question = this.qRepository.findById(id);
    if(question.isPresent())
      return question.get();
    else
      throw new DataNotFoundException("question not found");
  }

  public void create(String subject, String content, SiteUser author) {
    Question q = new Question();
    q.setSubject(subject);
    q.setContent(content);
    q.setAuthor(author);
    q.setCreateDate(LocalDateTime.now());

    this.qRepository.save(q);
  }

  public void create(Question q) {
    this.qRepository.save(q);
  }

  public void modify(Question question, String subject, String content) {
    question.setSubject(subject);
    question.setContent(content);
    question.setModifyDate(LocalDateTime.now());
    this.qRepository.save(question);
  }

  public void delete(Question question) {
    this.qRepository.delete(question);
  }

  public void vote(Question question, SiteUser siteUser) {
    question.getVoter().add(siteUser);
    this.qRepository.save(question);
  }
  private Specification<Question> search(String kw) {
    return new Specification<>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
        query.distinct(true);  // 중복을 제거
        Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
        Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
        Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
        return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
      }
    };
  }

//  public Page<Question> getList(int page, String kw) {
//    List<Sort.Order> sorts = new ArrayList<>();
//    sorts.add(Sort.Order.desc("createDate"));
//    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//    Specification<Question> spec = search(kw);
//    return this.qRepository.findAll(spec, pageable);
//  }

}
