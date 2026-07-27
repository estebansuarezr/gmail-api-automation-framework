package interactions;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

import java.io.FileOutputStream;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AttachFileFromDrive implements Interaction {

    @Override
    public <T extends Actor> void performAs(T actor) {

        try {

            Drive drive = actor.recall("drive");
            MimeMessage mime = actor.recall("mime");
            String fileName = actor.recall("driveFileName");

            FileList result = drive.files()
                    .list()
                    .setQ("name='" + fileName + "' and trashed=false")
                    .setFields("files(id,name)")
                    .execute();

            if (result.getFiles().isEmpty()) {
                throw new RuntimeException("No se encontró el archivo: " + fileName);
            }

            File driveFile = result.getFiles().get(0);

            java.io.File localFile = new java.io.File(
                    System.getProperty("java.io.tmpdir"),
                    driveFile.getName()
            );

            try (FileOutputStream output = new FileOutputStream(localFile)) {

                drive.files()
                        .get(driveFile.getId())
                        .executeMediaAndDownloadTo(output);
            }

            Multipart multipart;

            Object content = mime.getContent();

            if (content instanceof Multipart) {
                multipart = (Multipart) content;
            } else {
                multipart = new MimeMultipart();
            }

            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(localFile);

            multipart.addBodyPart(attachment);

            mime.setContent(multipart);

            actor.remember("mime", mime);

        } catch (Exception e) {
            throw new RuntimeException("Error adjuntando archivo desde Drive.", e);
        }
    }

    public static AttachFileFromDrive now() {
        return instrumented(AttachFileFromDrive.class);
    }
}