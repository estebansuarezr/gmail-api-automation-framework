package tasks;

import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import models.EmailData;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.util.Properties;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CreateEmail implements Task {

    private final EmailData email;

    public CreateEmail(EmailData email) {
        this.email = email;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        try {

            Session session = Session.getInstance(new Properties());

            MimeMessage mime = new MimeMessage(session);

            mime.setRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(email.getTo())
            );

            mime.setSubject(email.getSubject());

            MimeBodyPart body = new MimeBodyPart();
            body.setText(email.getBody());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(body);

            mime.setContent(multipart);

            actor.remember("mime", mime);
            actor.remember("driveFileName", email.getDriveFileName());

        } catch (Exception e) {
            throw new RuntimeException("Error creando el correo.", e);
        }
    }

    public static CreateEmail withData(String to, String subject, String body, String driveFileName) {

        EmailData email = new EmailData(
                to, subject, body, driveFileName
        );

        return instrumented(CreateEmail.class, email);
    }
}