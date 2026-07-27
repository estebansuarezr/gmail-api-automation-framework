package services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.gmail.GmailScopes;

import java.util.List;

public class DriveServiceFactory {

    private static final String APPLICATION_NAME = "Framework Gmail";

    private DriveServiceFactory() {
    }

    public static Drive build() {

        try {

            return new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    GoogleAuthorization.authorize(
                            List.of(
                                    GmailScopes.GMAIL_SEND,
                                    DriveScopes.DRIVE
                            )
                    )
            )
                    .setApplicationName(APPLICATION_NAME)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error creando Drive Service", e);
        }
    }
}
