package services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GoogleAuthorization {

    private static final String TOKENS_DIRECTORY = "tokens";

    private GoogleAuthorization() {
    }

    public static Credential authorize(List<String> scopes) throws Exception {

        NetHttpTransport httpTransport =
                GoogleNetHttpTransport.newTrustedTransport();

        InputStream input =
                GoogleAuthorization.class
                        .getClassLoader()
                        .getResourceAsStream("credentials.json");

        if (input == null) {
            throw new RuntimeException("No se encontró credentials.json");
        }

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(
                        GsonFactory.getDefaultInstance(),
                        new InputStreamReader(input)
                );

        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport,
                        GsonFactory.getDefaultInstance(),
                        clientSecrets,
                        scopes
                )
                        .setDataStoreFactory(
                                new FileDataStoreFactory(
                                        new java.io.File(TOKENS_DIRECTORY)))
                        .setAccessType("offline")
                        .build();

        LocalServerReceiver receiver =
                new LocalServerReceiver.Builder()
                        .setPort(8888)
                        .build();

        return new AuthorizationCodeInstalledApp(flow, receiver)
                .authorize("user");
    }
}