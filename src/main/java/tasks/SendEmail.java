package tasks;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.internet.MimeMessage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SendEmail implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        try {

            Gmail gmail = actor.recall("gmail");

            MimeMessage mime = actor.recall("mime");

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            mime.writeTo(buffer);

            String encodedEmail = Base64.getUrlEncoder()
                    .encodeToString(buffer.toByteArray());

            Message message = new Message();
            message.setRaw(encodedEmail);

            Message sentMessage = gmail.users()
                    .messages()
                    .send("me", message)
                    .execute();

            actor.remember("messageId", sentMessage.getId());

        } catch (Exception e) {
            throw new RuntimeException("Error enviando el correo.", e);
        }
    }

    public static SendEmail now() {
        return instrumented(SendEmail.class);
    }
}