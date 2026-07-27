# Automatización de Envío de Correos con Gmail API

## Descripción

Este proyecto implementa un framework de automatización utilizando Serenity BDD y el patrón Screenplay para enviar correos electrónicos mediante la API de Gmail. Además, permite adjuntar archivos almacenados en Google Drive.

---

## Tecnologías utilizadas

- Java 17
- Gradle
- Serenity BDD
- Screenplay Pattern
- Selenium WebDriver
- Gmail API
- Google Drive API
- Google OAuth 2.0
- JUnit 5

---

## Estructura del proyecto

```
src
 ├── main
      ├── java
      │      ├── tasks
      │      ├── interactions
      │      ├── questions
      │      ├── models
      │      ├── services
 └── test
      ├── java
      │      ├── stepdefinitions
      │      └── runners
      │
      └── resources
             ├── features
             └── credentials.json
```

---

## Prerrequisitos

- JDK 17
- Gradle 8+
- Google Chrome
- ChromeDriver compatible
- IntelliJ IDEA (opcional)

---

## Configuración

### 1. Crear un proyecto en Google Cloud

1. Crear un proyecto.
2. Habilitar:

- Gmail API
- Google Drive API

3. Crear un OAuth Client ID.

4. Descargar el archivo

```
credentials.json
```

5. Copiar el archivo en

```
src/test/resources/
```

---

### 2. Primera autenticación

Durante la primera ejecución se abrirá el navegador para autorizar la cuenta de Gmail.

Después de autorizar el acceso se generará automáticamente la carpeta

```
tokens/
```

La carpeta contiene el token de autenticación y no será necesario volver a iniciar sesión.

---

## Ejecución

Desde IntelliJ

Ejecutar:

```
Runner.java
```

o desde consola

```
gradlew clean test
```

---

## Escenario automatizado

El escenario realiza las siguientes acciones:

- Autenticación mediante OAuth 2.0
- Conexión con Gmail API
- Obtención de un archivo desde Google Drive
- Creación del mensaje
- Adjuntar el archivo
- Envío del correo
- Validación de envío exitoso

---

## Feature

```gherkin
Feature: Sending Email Using the Gmail API

  Scenario Outline: Sending an Email with an Attachment from Google Drive

    Given the user signs in to Google
    When the user creates an email with "<to>" "<subject>" "<body>" "<driveFileName>"
    And attaches a file from Google Drive
    Then the email is sent

    Examples:
      | to  | subject | body  | driveFileName |

```

---

## Arquitectura

El framework implementa el patrón Screenplay.

```
Actor
   │
   ▼
Task
   │
   ▼
Interaction
   │
   ▼
Google Services
   │
   ├── Gmail API
   └── Drive API
```

---

## Dependencias principales

- serenity-core
- serenity-screenplay
- serenity-junit5
- google-api-client
- google-api-services-gmail
- google-api-services-drive
- jakarta.mail

---

## Reportes

Para generar el reporte de Serenity ejecutar:

```
gradlew aggregate
```

El reporte se genera en:

```
target/site/serenity/index.html
```

o según la versión de Serenity

```
build/reports/serenity/index.html
```

---

## Autor

Juan Esteban Suárez Ramos