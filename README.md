# JMPSL

[![][jmpsl jitpack badge]][jmpsl jitpack]
&nbsp;&nbsp;
![](https://img.shields.io/badge/License-MIT-brown.svg)
&nbsp;&nbsp;
![](https://img.shields.io/badge/Made%20in-Java%2017%20with%20Gradle-1abc9c.svg)

[![][jmpsl core badge]][jmpsl core mvn]
&nbsp;&nbsp;
[![][jmpsl communication badge]][jmpsl communication mvn]
&nbsp;&nbsp;
[![][jmpsl file badge]][jmpsl file mvn]
&nbsp;&nbsp;
[![][jmpsl gfx badge]][jmpsl gfx mvn]
&nbsp;&nbsp;
[![][jmpsl security badge]][jmpsl security mvn]
&nbsp;&nbsp;
[![][jmpsl oauth2 badge]][jmpsl oauth2 mvn]

[jmpsl jitpack]: https://jitpack.io/#pl.miloszgilga/jmpsl
[jmpsl jitpack badge]: https://img.shields.io/jitpack/version/pl.miloszgilga/jmpsl?color=gree&label=JMPSL%20JitPack

[jmpsl core badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-core?label=JMPSL%20Core%20%28Central%29
[jmpsl communication badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-communication?label=JMPSL%20Communication%20%28Central%29
[jmpsl file badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-file?label=JMPSL%20File%20%28Central%29
[jmpsl gfx badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-gfx?label=JMPSL%20GFX%20%28Central%29
[jmpsl security badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-security?label=JMPSL%20Security%20%28Central%29
[jmpsl oauth2 badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-oauth2?label=JMPSL%20OAuth2%20%28Central%29

[jmpsl core mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core
[jmpsl communication mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-communication
[jmpsl file mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-file
[jmpsl gfx mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-gfx
[jmpsl security mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-security
[jmpsl oauth2 mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-oauth2

Java multi purpose Spring library (in short JMPSL) it's a multi-module development library project for rapidly building
REST-type services in the **Spring Boot framework**. It provides services for authentication, authorization, creating raster
graphics, sending files via FTP, messaging using popular communication protocols, and much more.

<a name="table-of-content"></a>
## Table of content
* [Modules](#modules)
* [Installation](#installation)
* [Versioning strategy](#versioning-strategy)
* [Author](#author)
* [Project status](#project-status)
* [License](#license)

<table>
    <tbody>
        <tr>
            <td rowspan="2">Modules</td>
            <td><a href="#module-core">JMPSL Core</a></td>
            <td><a href="#module-communication">JMPSL Communication</a></td>
            <td><a href="#module-file">JMPSL File</a></td>
        </tr>
        <tr>
            <td><a href="#module-gfx">JMPSL Gfx</a></td>
            <td><a href="#module-security">JMPSL Security</a></td>
            <td><a href="#module-oauth2">JMPSL OAuth2</a></td>
        </tr>
    </tbody>
</table>


<a name="module"></a>
## Modules
Library consists of the following modules:
* `JMPSL Core` -  main module: configuration, data access and utilities classes,
* `JMPSL Communication` - provide classes for point-to-point communication (email, websockets etc.),
* `JMPSL File` - provide classes for generators, receivers and senders to local or remove FTP server,
* `JMPSL Gfx` - provide classes for 2D raster images generators and manipulators,
* `JMPSL Security` - provide classes which extending Spring Security (for most typical REST API scenarios),
* `JMPSL OAuth2` - provide classes for OAuth2 Spring Security authorization via stateless REST endpoints.


<a name="installation"></a>
## Installation
### Stable releases, from Maven Central

* for Maven project:
```xml
<dependency>
    <groupId>pl.miloszgilga</groupId>
    <artifactId>jmpsl-[MOD]</artifactId>
    <version>X.Y.Z_AA</version>
</dependency>
```

* for Gradle project with Groovy:
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'pl.miloszgilga:jmpsl-[MOD]:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("pl.miloszgilga:jmpsl-[MOD]:X.Y.Z_AA")
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
        <artifactId>jmpsl-[MOD]</artifactId>
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
    implementation 'pl.miloszgilga.jmpsl:jmpsl-[MOD]:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
  maven { url = 'https://jitpack.io' }
}

dependencies {
    implementation("pl.miloszgilga.jmpsl:jmpsl-[MOD]:X.Y.Z_AA")
}
```

where: 
* `X.Y.Z_AA` is the selected version
* `[MOD]` is one of the selected module (`core`, `communication`, `file`,
`gfx`, `security` or `oauth2`).

For more info about installation script and integrate with other Java build project
technologies, go to [official Maven repository website](https://mvnrepository.com/artifact/pl.miloszgilga).

### Download JAR archives and generate SHA1
If you are not using any build system, you can optionally download the .jar archive from following links:
* [from MAVEN CENTRAL - stable releases](https://repo.maven.apache.org/maven2/pl/miloszgilga/)
* from JitPack - snapshot releases: <br> create link via pattern
  `jitpack.io/pl/miloszgilga/jmpsl/jmpsl-[MOD]/X.Y.Z_AA/jmpsl-[MOD]-X.Y.Z_AA-sources.jar` for sources, where:
  * `X.Y.Z_AA` is the selected library version
  * `[MOD]` is one of the module (`core`, `communication`, `file`, `gfx`, `security` or `oauth2`)

For check SHA1 checksum, download file with `.sha1` extension and generate checksum of downloaded `.jar` file via 
on UNIX systems:
```
$ sha1sum [filePath]
```
where `[filePath]` is the path to downloaded source `.jar`. For Windows, you can use 
[this website](https://emn178.github.io/online-tools/sha1_checksum.html).

> NOTE: At this moment, SHA1 checksums of source files are not available on JitPack.


<a name="versioning-strategy"></a>
## Versioning strategy
Stable, tested versions are available in the official **Maven** repositories. New, not fully tested versions (alpha,
beta and snapshots) will be posted in the **JitPack** repository. To find, how to add a library to your project
go to `Installation section` for selected module.<br><br>
Versions of this library are annotated by `X.Y.Z_AA`, where `AA` is the next iteration of testing, for example, the 
full version is `1.0.2` and the test version of this full version is `1.0.2_04`.


<a name="module-core"></a>
## JMPSL Core [[page](https://github.com/Milosz08/jmpsl/tree/master/jmpsl-core)]
[![][jmpsl core badge]][jmpsl core mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br><br>
Main module: configuration, data access and utilities classes.

### Basic usage
1. Apply following properties in `application.properties` or `application.yml`:
```properties
# avaialble application locales (defined as array list, for ex. <i>fr,pl,en_GB,en_US</i>). Property not required.
# by default it is en_US.
jmpsl.core.locale.available-locales = en_US,pl

# default selected application locale (defined as locale string, for ex. <i>en_US</i>). Property not required.
# by default it is en_US.
jmpsl.core.locale.default-locale = en_US

# default locale message bundles. Property not required. Default location is in classpath: 'i18n/messages'.
# accept multiple values separated by period's
jmpsl.core.locale.messages-paths = i18n-api/messages,i18n-mail/messages,i18n-jpa/messages
```

### i18n API implementation (internationalization)
1. To initialized i18n in your application, firstly create resources bundle in `resources/i18n` directory. Resource files 
should be named as `messages_[lang].properties`, where `[lang]` is the language prefix (ex. en-US, pl, fr etc.). Example:
```properties
# messages_en.properties
app.exception.NumberFormatException = Incorrect number format.

# messages_pl.properties
app.exception.NumberFormatException = Nieprawidłowy format liczbowy.
```
2. In created resources insert key <> values pairs with messages.
3. Create following enum class extends `org.jmpsl.core.i18n.ILocaleEnumSet` interface:
```java
public enum AppLocaleSet implements ILocaleEnumSet {
    INCORRECT_NUMBER_FORMAT_EXC("app.exception.NumberFormatException");
    
    private final String holder;

    @Override
    public String getHolder() {
        return holder;
    }
}
```
4. Sample usage in Spring Bean:
```java
@Component
public class InternationalizationExample {

    private final LocaleMessageService localeMessageService;

    // constructor for dependency injections
    
    public void showMessage() {
        final String message = localeMessageService
            .getMessage(AppLocaleSet.INCORRECT_NUMBER_FORMAT_EXC);
        System.out.println(message);
    }
}
```

### Exceptions listener
1. For create basic custom exception listener, create Spring Bean extending 
`org.jmpsl.core.exception.AbstractBaseRestExceptionListener` class. In basic, library catching following exceptions:
   * `org.jmpsl.core.exception.RestServiceServerException`
   * `org.jmpsl.core.exception.RestServiceAuthServerException`
   * `org.springframework.web.servlet.NoHandlerFoundException` [See NOTE below]
   * `org.springframework.web.bind.MethodArgumentNotValidException`
   * `org.springframework.http.converter.HttpMessageNotReadableException`
   * `org.springframework.http.converter.HttpMessageNotReadableException`
   * `java.lang.Exception`

   For others exception, create own exception listener (annotated by `org.springframework.web.bind.annotation.ExceptionHandler`).
```java
@RestControllerAdvice
public class ExceptionsListener extends AbstractBaseRestExceptionListener {
    ExceptionsListener(LocaleMessageService messageService) {
        super(messageService);
    }
    
    // here you can insert optional exceptions listeners
}
```

> NOTE: For `org.springframework.web.servlet.NoHandlerFoundException` properly handling exception capture, you need 
> to add the following entries in the `application.properties` or `application.yml` file: 
> ```properties
> spring.web.resources.add-mappings = false
> spring.mvc.log-resolved-exception = false
> spring.mvc.throw-exception-if-no-handler-found = true
> ```

<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="module-communication"></a>
## JMPSL Communication [[page](https://github.com/Milosz08/jmpsl/tree/master/jmpsl-communication)]
[![][jmpsl communication badge]][jmpsl communication mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br><br>
Provide classes for point-to-point communication (email, websockets etc.),

Required artifacts: `jmpsl-core`. Corresponding versions.<br>
Required spring boot starter: `spring-boot-starter-mail`. Min version: `3.0.2`

### Basic usage

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
    SAMPLE_TEMPLATE                 ("/sample-template.template.ftl");
    
    private final String templateName;
    
    @Override
    public String getTemplateName() {
        return templateName;
    }
}
```
4. Build mail message with `org.jmpsl.communication.mail.MailRequestDto` class:

```java
import java.util.HashMap;

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

<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="module-file"></a>
## JMPSL File [[page](https://github.com/Milosz08/jmpsl/tree/master/jmpsl-file)]
[![][jmpsl file badge]][jmpsl file mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br><br>
Provide classes for generators, receivers and senders to local or remote FTP server.

Required artifacts: `jmpsl-core`. Corresponding versions.

### Basic usage

1. Apply following properties in `application.properties` or `application.yml`:
```properties
# define, if SSH/SFTP service is active. By default "true". Property required.
jmpsl.file.ssh.active = true

# define SSH/SFTP service host address. By default "127.0.0.1" (localhost). Property required.
jmpsl.file.ssh.socket-host = 127.0.0.1

# define SSH/SFTP service login. Property required.
jmpsl.file.ssh.socket-login = sampleLogin123

# define file name for known hosts in SSH/SFTP service. By default "known_hosts.dat". Property required.
# File must be located in ROOT project directory.
jmpsl.file.ssh.known-hosts-file-name = known_hosts.dat

# define SFTP server address. By default "127.0.0.1" (localhost). Property required.
jmpsl.file.ssh.user-private-key-file-name = id_rsa

# define SFTP server address. By default "127.0.0.1" (localhost). Property required.
jmpsl.file.sftp.server-url = 127.0.0.1

# define SSH/SFTP path from server root to domain directory. Property required. Property must be end with "/" character.
jmpsl.file.basic-external-server-path = /external-file-server-path

# define SSH/SFTP directory name for application static resources. By default "". Property required. Property
# cannot be end with "/" character.
jmpsl.file.app-external-server-path = /static-images

# define file name hash generator separator. By default "-". Property non-required.
jmpsl.file.hash-code.separator = "-"

# single sequences count in all hash word. By default "4". Property required. Only unsigned values (1-255).
jmpsl.file.hash-code.count-of-sequences = 4

# Define count of characters in single hash sequence. By default "5". Property required. Only unsigned values (1-255).
jmpsl.file.hash-code.sequence-length = 5
```

### Basic usage

```java
@Service
public class SftpConnecion {
    
    private final SshFileSocketConnector connector;

    // constructor for dependency injections
    
    // perform connection in legacy Java style
    public void makeConnectionLegacy() {
        connector.connectToSocketAndPerformAction(new ISshFileSocketExecutor() {
            @Override
            public void execute(StatefulSFTPClient sftpClient) {
                // insert here code perform action on SFTP Client
                // any exception return status code 500 and RestAPI respone JSON object
            }
        });
    }
    
    // perform connection in new Java style
    public void makeConnection() {
        connector.connectToSocketAndPerformAction(client -> {
            // insert here code perform action on SFTP Client
            // any exception return status code 500 and RestAPI respone JSON object
        });
    }
}
```

<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="module-gfx"></a>
## JMPSL GFX [[page](https://github.com/Milosz08/jmpsl/tree/master/jmpsl-gfx)]
[![][jmpsl gfx badge]][jmpsl gfx mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br><br>
Provide classes for 2D raster images generators and manipulators.

Required artifacts: `jmpsl-core`, `jmpsl-file`. Corresponding versions.

### Basic usage

1. Apply properties from `JMPSL File` module for SSH/SFTP external server connection.
2. Apply following properties in `application.properties` or `application.yml`:
```properties
# SSH/SFTP server path for static user graphics resources. Property required.
jmpsl.gfx.user-gfx.static-images-content-path = images

# font location for user default profile image generator. Property not required. Font file should be in
# /resources directory
jmpsl.gfx.user-gfx.preferred-font-link = static/font/noto-serif-v21-latin-regular.ttf

# font name for user default profile image generator. Property required
jmpsl.gfx.user-gfx.preferred-font-name = Noto Serif

# colors which usage in generated user avatar images. Property not required. Default colors:
#   "#f83f3d", "#fe5430", "#ff9634", "#ffbf41", "#cad958", "#85c15d", "#029489", "#00bcd2", "#1197ec",
#   "#4151b0", "#6a3ab0", "#a128a9", "#ee1860",
jmpsl.gfx.user-gfx.preferred-hex-colors = #00ff00,#2254fc

# preffered foreground for user avatar generator. Property not required. By default it is #ffffff (white)
jmpsl.gfx.user-gfx.preferred-foreground-color = #ffffff
```

### Image generators and serders via API example
```java
@Service
class ProfileImage {
    
    private final UserImageSftpService imageSftpService;
    
    // constructor for dependency injections
    
    public void createOrUpdate() {
        // create buffered image generator payload
        final BufferedImageGeneratorPayload payload = BufferedImageGeneratorPayload.builder()
            .id(1L)
            .imageUniquePrefix("profile")
            .fontSize(14) // 14pt
            .size(100) // 100px x 100px
            .initials(new char[] { 'b', 'a' })
            .build();
        // created or updated image with sample name: "profile_16943204364521622266.png" 
        // in SFTP server directory: "/user1_7LFAC-XxrJC-aZi0E-lbGxH"

        // generate image and send to SFTP server 
        final BufferedImageGeneratorRes response = imageSftpService
            .generateAndSaveDefaultUserImage(payload, ImageExtension.PNG);
        // show saved image location in remove SFTP server
        System.out.println(response.getBufferedImageRes().getLocation());
    }
    
    public void sendOrUpdate() {
        // create buffered image sender payload
        final BufferedImageSenderPayload imageSenderPayload = BufferedImageSenderPayload.builder()
            .id(1L)
            .imageUniquePrefix("profile")
            .bytesRepresentation(/* insert image as bytes representation */)
            .preferredWidth(100) // 100px, available rescaling
            .preferredHeight(100) // 100px, available rescaling
            .userHashCode("7LFAC-XxrJC-aZi0E-lbGxH")
            .build();
        // created or updated image with sample name: "profile_16943204364521622266.png" 
        // in SFTP server directory: "/user1_7LFAC-XxrJC-aZi0E-lbGxH"
        
        // send image to SFTP server
        final BufferedImageRes response = userImageSftpService
            .saveUserImage(imageSenderPayload, ImageExtension.PNG);
        // show saved image location in remove SFTP server
        System.out.println(response.getBufferedImageRes().getLocation());
    }
    
    public void delete() {
        // create buffered image delete payload
        final BufferedImageDeletePayload deletePayload = BufferedImageDeletePayload.builder()
            .id(1L)
            .uniqueImagePrefix("profile")
            .userHashCode("7LFAC-XxrJC-aZi0E-lbGxH")
            .build();
        // delete image from SFTP server
        userImageSftpService.deleteUserImage(deletePayload);
    }
}
```
> NOTE: If the image in the specified folder already exists (same ID, hash and unique image prefix), it will be overwritten.

On any exception during saving or sending file to external SFTP server, application will return
JSON stardard error response with code, time and additional informations. Standard WebAPI exception
class you will find in `JMPSL Core` module.


<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="module-security"></a>
## JMPSL Security [[page](https://github.com/Milosz08/jmpsl/tree/master/jmpsl-security)]
[![][jmpsl security badge]][jmpsl security mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br><br>
Provide classes which extending Spring Security (for most typical REST API scenarios).

### Basic usage

1. Add Spring Boot Security starter:
* for Maven projects
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>3.X.Y</version>
</dependency>
```
* for Gradle with Groovy projects
```groovy
implementation 'org.springframework.boot:spring-boot-starter-security:3.X.Y'
```
* for Gradle with Kotlin projects
```kotlin
implementation("org.springframework.boot:spring-boot-starter-security:3.X.Y")
```

2. Apply following properties in `application.properties` or `application.yml`:
```properties
# OTA (One Time Access) token elapsed time in minutes. Property not required. By default it is 10 minutes
jmpsl.security.ota.length = 10

# password encoder strength (reccomended from 8 to 12). Property not required. By default it is 8 units
jmpsl.security.password-encoder-strength = 8

# JWT secret key (salt). Property required.
jmpsl.security.jwt.secret = fm92400mfomvnoifd1039cmoivmoeifmd01390d9fomfv

# JWT issuer (key signatory). Property required.
jmpsl.security.jwt.issuer = applicationName

# time (in minutes) after JWT is expired. Property not required. By default it is 5 minutes
jmpsl.security.jwt.expired-minutes = 5

# time (in days) after refresh token is expired. Property not required. By default it is 30 days
jmpsl.security.jwt.refresh-token-expired-days = 30

# enabled address in CORS policy (for more info check https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS). Property required.
jmpsl.security.cors.client = http://127.0.0.1:4200

# max CORS alive time in milis. Property not required. By default it is 3600 milis
jmpsl.security.cors.max-age = 3600
```

3. Create entity class represents User database model and implements `IAuthUserModel` interface:
```java
@Entity
public class UserEntity implements Serializable, IAuthUserModel<SimpleGrantedRole> { 
    @Serial private static final long serialVersionUID = 1L;

    // standard hibernate columns, getters, setters and non arguments constructor
    
    @Override
    public String getAuthUsername() {
        // return here user unique identifier (user name or email address)
    }
    
    @Override
    public String getAuthPassword() {
        // return here user password (hashable form data)
    }
  
    @Override
    public Set<SimpleGrantedRole> getAuthRoles() {
        // return user roles as Set of strings, ex. USER, ADMIN, OWNER
        return SimpleGrantedRole.getSetCollection(); // return default roles
    }

    @Override
    public boolean isAccountEnabled() {
        // return true, if account is enabled. NOTE! By default, returns false.
        return true;
    }
    // ...
}
```

4. Create service class for loading user to Spring Security Context:
```java
@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Override
    public AuthUser<SimpleGrantedRole> loadUserByUsername(String identifier) {
        // identifier is getting by getAuthUsername() method from IAuthUserModel interface
        // get user from database and return user entity object
    }
}
```

5. Create standard `OncePerRequestFilter` filter for validating JWT and setting Spring Security Context. You can use
default pre-defined `AbstractJwtRequestFilter` from `JMPSL Security` module or create own custom filter:
```java
@Component
public class JwtAuthenticationFilter extends AbstractJwtRequestFilter {

   public JwtAuthenticationFilter(JwtService jwtService, AuthUserDetailsService details) {
      // extract and validate user by functional expression. Take token from request and return user identifier or null
      super(jwtService, details, token -> {
         /* extracted user details from JWT */
      });
   }
}
```

6. Create configurable class for Spring Security OAuth2 configuration (sample configurable class):
```java
@Configuration
public class SpringSecurityConfigurer {
    private final Environment env;
    
    private final AuthResolverForRest authResolverForRest;
    private final MiddlewareExceptionFilter middlewareExceptionFilter;
    private final AccessDeniedResolverForRest accessDeniedResolverForRest;

    // injected authentication filter from step 5
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    // constructor for fields injection

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(options -> options.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(middlewareExceptionFilter, LogoutFilter.class)
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authResolverForRest)
                .accessDeniedHandler(accessDeniedResolverForRest)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/error").permitAll()
                // insert here requestMatchers
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
```

<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="module-oauth2"></a>
## JMPSL OAuth2 [[page](https://github.com/Milosz08/jmpsl/tree/master/jmpsl-oauth2)]
[![][jmpsl oauth2 badge]][jmpsl oauth2 mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br><br>
Provide classes for OAuth2 Spring Security authorization via stateless REST endpoints. At this moment, support following
OAuth2 authentication providers:
* Facebook
* Google
* Github
* LinkedIn

Required artifacts: `jmpsl-core`, `jmpsl-security`. Corresponding versions.<br>
Required spring boot starter: `spring-boot-starter-oauth2-client`. Min version: `3.0.2`

### Data flow:
1. Client make request for address `http://127.0.0.1:4200/oauth2/authorization/[supplier]?base_uri=[base]&after_login_uri=[login]&after_signup_uri=[signup]`, where
   * `[supplier]` is the supplier name (facebook, google)
   * `[base]` is the base frontend application URL, ex. `http://127.0.0.1:4200`
   * `[login]` is the redirect address suffix after successfully login new user, ex. `auth/login`
   * `[signup]` is the redirect address suffix after successfully register, ex. `auth/after-register`
2. For login scenario after successfully authenticated, server make redirect to `http://127.0.0.1:4200/auth/login?token=[rawToken]`,
where `[rawToken]` is the generated JWT from API (similarly, the data flow will be represented by the registration action).
3. On some authentication exception, server make redirect to `http://127.0.0.1:4200/auth/[action]?error=[message]`, where
   * `[action]` is the one of the suffix, ex. `login` or `after-register`
   * `[message]` is the specific error message

### Basic usage

1. Add Spring Boot OAuth2 Client starter:
* for Maven projects
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-client</artifactId>
    <version>3.X.Y</version>
</dependency>
```
* for Gradle with Groovy projects
```groovy
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client:3.X.Y'
```
* for Gradle with Kotlin projects
```kotlin
implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.X.Y")
```

2. Apply following properties in `application.properties` or `application.yml` (example for facebook and google providers):
```properties
# standard Spring Boot OAuth2 starter properties, requireds
security.oauth2.client.registration.google.clientId = <insert here google client id>
security.oauth2.client.registration.google.clientSecret = <insert here google client key>
security.oauth2.client.registration.facebook.clientId = <insert here facebook client id>
security.oauth2.client.registration.facebook.clientSecret = <insert here facebook client key>

# true, if OAuth2 is active, false if is not active. WARN! If OAuth2 is active, in the absence of any below the required
# property, program will immediately terminate with an error
jmpsl.security.oauth2-active = true

# time (in minutes) after which cookie from external OAuth2 authentication server is expired. Property not required; by
# default is 3 minutes
jmpsl.oauth2.cookie-expired-minutes = 3

# list of all supported suppliers by application. Available options: facebook, google, github, linkedin. Property required.
jmpsl.oauth2.available-suppliers = google,facebook

# redirect URLs after successfull or failure authentication via OAuth2. Separated by comma. Property required.
jmpsl.oauth2.redirect-uris = http://localhost:4200/oauth2/redirect
```

3. Create entity class represents User database model and implements `IAuthUserModel` interface:
```java
@Entity
public class UserEntity implements Serializable, IAuthUserModel<SimpleGrantedRole> { 
    @Serial private static final long serialVersionUID = 1L;

    // standard hibernate columns, getters, setters and non arguments constructor
    
    @Override
    public String getAuthUsername() {
        // return here user unique identifier (user name or email address)
    }
    
    @Override
    public String getAuthPassword() {
        // ONLY FOR LOCAL ACCOUNTS: return user password, otherwise (if your authnetication method
        // is only realized by OAuth2, return null)
    }
  
    @Override
    public Set<SimpleGrantedRole> getAuthRoles() {
        // return user roles as Set of strings, ex. USER, ADMIN, OWNER
        return SimpleGrantedRole.getSetCollection(); // return default roles
    }

    @Override
    public boolean isAccountEnabled() {
        // return true, if account is enabled. NOTE! By default, returns false.
        return true;
    }
}
```

4. Create service class, which implementing `IOAuth2LoaderService` interface:
```java
@Service
public class OAuth2Service implements IOAuth2LoaderService<SimpleGrantedRole> {
    @Override
    public OAuth2UserExtender<SimpleGrantedRole> registrationProcessingFactory(OAuth2RegistrationDataDto registrationData) {
        // persist or update OAuth2 user details and return fabricated user using fabricateUser()
        // method from OAuth2Util class
    }
}
```

5. Create service class which generating JWT for OAuth2 purposes:
```java
@Service
public class JwtGenerator implements IOAuth2TokenGenerator {
    @Override
    public String generateToken(Authentication auth) {
        // generate JWT based principals from Authentication class and return raw token
    }
}
```

6. Create service class for loading user to Spring Security Context:
```java
@Service
public class AuthUserDetailsService implements UserDetailsService {
    @Override
    public OAuth2UserExtender<SimpleGrantedRole> loadUserByUsername(String identifier) {
        // identifier is getting by getAuthUsername() method from IAuthUserModel interface
        // get user from database and return fabricated user using fabricateUser() method from OAuth2Util class
    }
}
```

7. Create standard `OncePerRequestFilter` filter for validating JWT and setting Spring Security Context. You can use
default pre-defined `AbstractJwtRequestFilter` from `JMPSL Security` module or create own custom filter:
```java
@Component
public class JwtAuthenticationFilter extends AbstractJwtRequestFilter {

   public JwtAuthenticationFilter(JwtService jwtService, AuthUserDetailsService details) {
      // extract and validate user by functional expression. Take token from request and return user identifier or null
      super(jwtService, details, token -> {
         /* extracted user details from JWT */
      });
   }
}
```

8. Create configurable class for Spring Security OAuth2 configuration (sample configurable class):
```java
@Configuration
public class SpringSecurityConfigurer {
    private final Environment env;

    private final OAuth2OnFailureResolver oAuth2OnFailureResolver;
    private final MiddlewareExceptionFilter middlewareExceptionFilter;
    private final CookieOAuth2ReqRepository cookieOAuth2ReqRepository;
  
    private final AuthResolverForRest authResolverForRest;
    private final AccessDeniedResolverForRest accessDeniedResolverForRest;

    // injected service from step 4
    private final OAuth2Service oAuth2Service;
    // injected JWT generator from step 5
    private final JwtGenerator jwtGenerator;
    // injected authentication filter from step 7
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    // constructor for fields injection

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(options -> options.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(middlewareExceptionFilter, LogoutFilter.class)
            .formLogin().disable()
            .httpBasic().disable()
            .csrf().disable()
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authResolverForRest)
                .accessDeniedHandler(accessDeniedResolverForRest)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/error", "/oauth2/**").permitAll()
                // insert here requestMatchers
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login -> oauth2Login
                .authorizationEndpoint(p2p -> p2p.authorizationRequestRepository(cookieOAuth2ReqRepository))
                .redirectionEndpoint()
                .and()
                .userInfoEndpoint(info -> info
                    .oidcUserService(new AppOidcUserService(oAuth2Service))
                    .userService(new AppOAuth2UserService(env, oAuth2Service))
                )
                .tokenEndpoint(p2p -> p2p.accessTokenResponseClient(OAuth2Util.auth2AccessTokenResponseClient()))
                .successHandler(oAuth2OnSuccessfulResolver())
                .failureHandler(oAuth2OnFailureResolver)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
  
    @Bean
    public OAuth2OnSuccessfulResolver oAuth2OnSuccessfulResolver() {
        return new OAuth2OnSuccessfulResolver(env, jwtGenerator);
    }
}
```

<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="author"></a>
## Author
Created by Miłosz Gilga. If you have any questions about this application, send message: 
[personal@miloszgilga.pl](mailto:personal@miloszgilga.pl).


<a name="project-status"></a>
## Project status
Project is still in development.


<a name="license"></a>
## License
This application is on MIT License.
