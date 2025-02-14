package a.board;

import a.board.question.QRepository;
import a.board.question.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private QRepository qRepository;

	@Test
	void contextLoads() {
//		Question q1 = new Question();
//		q1.setSubject("첫번째 주제");
//		q1.setContent("h2 db 설정시(properties) 서버 포트와 db연결 포트를 다르게 해야합니다.");
//		q1.setCreateDate(LocalDateTime.now());
//		this.qRepository.save(q1);
//
//		Question q2 = new Question();
//		q2.setSubject("second 주제");
//		q2.setContent("h2 db; jdbc:h2:tcp//localhost:9092/~/local should be defferent server port");
//		q2.setCreateDate(LocalDateTime.now());
//		this.qRepository.save(q2);
//		Optional<Question> q = this.qRepository.findById(3);
//		assertTrue(q.isPresent());
//		Question question = q.get();
//		System.out.println(question.getContent()+","+question.getCreateDate());
//		this.qRepository.delete(question);
//		assertEquals(2, this.qRepository.count());
	}

}
