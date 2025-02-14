package a.board.question;

public class DataNotFoundException extends RuntimeException {
  public DataNotFoundException(String questionNotFound) {
    super(questionNotFound);
  }
}
