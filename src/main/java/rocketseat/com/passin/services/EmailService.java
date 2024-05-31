package rocketseat.com.passin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import rocketseat.com.passin.config.ErrorMessages;
import rocketseat.com.passin.domain.mail.exceptions.SendMailException;

@Service
@RequiredArgsConstructor
public class EmailService {
  @Autowired
  private final JavaMailSender javaMailSender;
  @Autowired
  private final TemplateEngine templateEngine;

  public void sendPinToEmail(String receiver, String pinCode) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      Context context = new Context();
      context.setVariable("pinCode", pinCode);

      String htmlContent = templateEngine.process("confirm-email", context);

      helper.setTo(receiver);
      helper.setSubject("Confirmação de e-mail");
      helper.setText(htmlContent, true);

      javaMailSender.send(message);
    } catch (MailSendException exception) {
      throw new SendMailException(ErrorMessages.SEND_CONFIRMATION_MAIL_ERROR);
    } catch (MessagingException exception) {
      throw new SendMailException(ErrorMessages.SEND_CONFIRMATION_MAIL_ERROR);
    }
  }
}
