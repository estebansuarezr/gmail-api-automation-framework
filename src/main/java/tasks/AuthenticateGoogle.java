package tasks;

import com.google.api.services.drive.Drive;
import com.google.api.services.gmail.Gmail;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import services.DriveServiceFactory;
import services.GmailServiceFactory;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AuthenticateGoogle implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {

        Gmail gmail = GmailServiceFactory.build();
        Drive drive = DriveServiceFactory.build();

        actor.remember("gmail", gmail);
        actor.remember("drive", drive);

    }

    public static AuthenticateGoogle usingApis() {
        return instrumented(AuthenticateGoogle.class);
    }
}