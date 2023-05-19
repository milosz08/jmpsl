# JMPSL Communication

![](https://img.shields.io/badge/Made%20in-Java%2017%20with%20Gradle-1abc9c.svg)
&nbsp;&nbsp;
![](https://img.shields.io/badge/License-MIT-brown.svg)

[![][jmpsl communication badge]][jmpsl communication mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br>

[jmpsl jitpack]: https://jitpack.io/#pl.miloszgilga/jmpsl
[jmpsl jitpack badge]: https://img.shields.io/jitpack/version/pl.miloszgilga/jmpsl?color=gree&label=JMPSL%20JitPack

[jmpsl communication badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-communication?label=JMPSL%20Communication%20%28Central%29
[jmpsl communication mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-communication

Provide classes for point-to-point communication (email, websockets etc.),

Required artifacts: `jmpsl-core`. Corresponding versions.<br>
Required spring boot starter: `spring-boot-starter-mail`. Min version: `3.0.2`

<a name="table-of-content"></a>
## Table of content
* [Installation](#installation)
* [Basic usage](#basic-usage)
* [Author](#author)
* [License](#license)

<a name="installation"></a>
## Installation
### Stable releases, from Maven Central

* for Maven project:
```xml
<dependency>
    <groupId>pl.miloszgilga</groupId>
    <artifactId>jmpsl-communication</artifactId>
    <version>X.Y.Z_AA</version>
</dependency>
```

* for Gradle project with Groovy:
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'pl.miloszgilga:jmpsl-communication:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("pl.miloszgilga:jmpsl-communication:X.Y.Z_AA")
}
```

### Snapshot releases, from JitPack repository

* for Maven project:
```xml
<repositories>
    <repository>
        <id>jitpack</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    ...
    <dependency>
        <groupId>pl.miloszgilga.jmpsl</groupId>
        <artifactId>jmpsl-communication</artifactId>
        <version>X.Y.Z_AA</version>
    </dependency>
    ...
</dependencies>
```

* for Gradle project with Groovy:
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'pl.miloszgilga.jmpsl:jmpsl-communication:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
  maven { url = 'https://jitpack.io' }
}

dependencies {
    implementation("pl.miloszgilga.jmpsl:jmpsl-communication:X.Y.Z_AA")
}
```

where `X.Y.Z_AA` is the selected version.

For more info about installation script and integrate with other Java build project
technologies, go to [official Maven repository website](https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core).


<a name="basic-usage"></a>
## Basic usage
1. Add Spring Boot Mail starter:
* for Maven projects
```xml
<dependencies>
    ...
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
        <version>3.X.Y</version>
    </dependency>
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>3.X.Y</version>
    </dependency>
    ...
</dependencies>
```
* for Gradle with Groovy projects
```groovy
implementation 'org.springframework.boot:spring-boot-starter-mail:3.X.Y'
implementation 'org.freemarker:freemarker:X.Y.Z'
```
* for Gradle with Kotlin projects
```kotlin
implementation("org.springframework.boot:spring-boot-starter-mail:3.X.Y")
implementation("org.freemarker:freemarker:X.Y.Z")
```
where:
* `X.Y.Z_AA` is the selected version

2. Apply following properties in `application.properties` or `application.yml`:
```properties
# define email Freemarker templates directory path. By default "classpath:/templates". Property required.
# templates should be in /resources directory.
jmpsl.communication.mail.freemarker-templates-dir = classpath:/templates

# external resource bundle file for i18n messages (in src/main/resources). Property not requred.
jmpsl.core.locale.messages-path = i18n-mail/messages,... others message bundles
```

### Email messages sender API
1. Create `messages_[lang].properties` in `i18n-mail` directory, where lang is i18n tag (pl, en-US etc.):
```properties
# messages_en.properties
sample.message = This is a sample message {0}.
# messages_pl.properties
sample.message = To jest testowa wiadomość {0}.
```

2. Create sample email template file in `/resources/templates` with `.ftl` extension.
```injectedfreemarker
<#assign sampleMessage = i18n("sample.message", [ injectedText ])>
<table>
    <td><p>${sampleMessage}</p></td>
</table>
```
3. Create enum implementing interface `org.jmpsl.communication.mail.IMailEnumeratedTemplate` with names of templates:
```java
public enum MailTemplate implements IMailEnumeratedTemplate {
    SAMPLE_TEMPLATE("/sample-template.template.ftl");
    
    private final String templateName;
    
    @Override
    public String getTemplateName() {
        return templateName;
    }
}
```
4. Build mail message with `org.jmpsl.communication.mail.MailRequestDto` class:

```java
@Service
class MailSender {

    private final JmpslMailService jmpslMailService;

    // constructor for dependency injections

    public void sendMessage() {
        // prepare mime message DTO
        final MailRequestDto requestDto = MailRequestDto.builder()
            .sendTo(Set.of(/* insert here all mail accounts */))
            .sendFrom(/* insert here server responder, ex. noreply@example.pl */)
            .setLocale(/* insert here locale for messages bundle */)
            .setRequest(/* optionally, if you can have access to "baseServletPath" in templates */)
            .setAppName(/* application name, property "appName" in templates */)
            .setReplyAddress(/* reply address for responding on auto-generated message */)
            .messageSubject("This is a sample message")
            .attachments(/* optionally, insert here all attachements as file handler and mime type */)
            .inlineResources(/* optionally, insert here embedded inline resources in mail message (ex. images) */)
            .build();

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("injectedText", "This is injected text.");
        // ... and more additional parameters
        
        // send message with passed request DTO and parameters with template name (in last argument)
        jmpslMailService.sendEmail(requestDto, parameters, MailTemplate.SAMPLE_TEMPLATE);
    }
}
```

<a name="author"></a>
## Author
Created by Miłosz Gilga. If you have any questions about this application, send message:
[personal@miloszgilga.pl](mailto:personal@miloszgilga.pl).


<a name="license"></a>
## License
This application is on MIT License.
