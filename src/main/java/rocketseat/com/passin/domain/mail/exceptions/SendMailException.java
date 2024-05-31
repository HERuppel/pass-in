package rocketseat.com.passin.domain.mail.exceptions;

public class SendMailException extends RuntimeException {
    public SendMailException(String message) {
        super(message);
    }
}
