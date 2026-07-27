Feature: Sending Email Using the Gmail API

  Scenario Outline: Sending an Email with an Attachment from Google Drive

    Given the user signs in to Google
    When the user creates an email with "<to>" "<subject>" "<body>" "<driveFileName>"
    And attaches a file from Google Drive
    Then the email is sent

    Examples:
      | to                         | subject          | body                                              | driveFileName |
      | esteban.suarezr@gmail.com  | Prueba Gmail API | Este correo fue enviado por jesuarezr89           | document.pdf |
      | pruebautomatizacionquipux@gmail.com  | Prueba Aut Quipux | Prueba de envío de correo Automatizado | document.pdf |