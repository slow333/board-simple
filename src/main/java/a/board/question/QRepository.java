package a.board.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRepository extends JpaRepository<Question, Integer> {

  Page<Question> findAll(Pageable pageable);

  Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}
