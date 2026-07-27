package models;

public class EmailData {

    private final String to;
    private final String subject;
    private final String body;
    private final String driveFileName;

    public EmailData(String to,
                     String subject,
                     String body,
                     String driveFileName) {

        this.to = to;
        this.subject = subject;
        this.body = body;
        this.driveFileName = driveFileName;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getDriveFileName() {
        return driveFileName;
    }

    @Override
    public String toString() {
        return "EmailData{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", driveFileName='" + driveFileName + '\'' +
                '}';
    }
}