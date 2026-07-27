package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import interactions.AttachFileFromDrive;
import tasks.AuthenticateGoogle;
import tasks.CreateEmail;
import tasks.SendEmail;

public class SendEmailStepDefinitions {

    private Actor actor;

    @Before
    public void setUp() {
        actor = Actor.named("Esteban");
    }

    @Given("the user signs in to Google")
    public void theUserSignsInToGoogle() {

        actor.attemptsTo(
                AuthenticateGoogle.usingApis()
        );
    }

    @When("the user creates an email with {string} {string} {string} {string}")
    public void theUserCreatesAnEmail(String to, String subject, String body, String driveFileName) {

        actor.attemptsTo(
                CreateEmail.withData(to, subject, body, driveFileName)
        );
    }

    @When("attaches a file from Google Drive")
    public void attachesAFileFromGoogleDrive() {

        actor.attemptsTo(
                AttachFileFromDrive.now()
        );
    }

    @Then("the email is sent")
    public void theEmailIsSent() {

        actor.attemptsTo(
                SendEmail.now()
        );

        String messageId = actor.recall("messageId");

        System.out.println("Email sent successfully.");
        System.out.println("Message ID: " + messageId);
    }
}
