package rocketseat.com.passin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
  @Autowired
  private final JavaMailSender javaMailSender;

  public void sendEmail(String receiver, String subject, String text) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(receiver);
      message.setSubject(subject);
      message.setText(text);
      javaMailSender.send(message);
    } catch (MailSendException exception) {
      throw new RuntimeException(exception);
    }
  }
}
