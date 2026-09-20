# JMPSL Core

![](https://img.shields.io/badge/Made%20in-Java%2017%20with%20Gradle-1abc9c.svg)
&nbsp;&nbsp;
![](https://img.shields.io/badge/License-Apache%202.0-brown.svg)

[![][jmpsl core badge]][jmpsl core mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br>

[jmpsl jitpack]: https://jitpack.io/#pl.miloszgilga/jmpsl
[jmpsl jitpack badge]: https://img.shields.io/jitpack/version/pl.miloszgilga/jmpsl?color=gree&label=JMPSL%20JitPack

[jmpsl core badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-core?label=JMPSL%20Core%20%28Central%29
[jmpsl core mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core

Main module: configuration, data access and utilities classes.

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
    <artifactId>jmpsl-core</artifactId>
    <version>X.Y.Z_AA</version>
</dependency>
```

* for Gradle project with Groovy:
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'pl.miloszgilga:jmpsl-core:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("pl.miloszgilga:jmpsl-core:X.Y.Z_AA")
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
        <artifactId>jmpsl-core</artifactId>
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
    implementation 'pl.miloszgilga.jmpsl:jmpsl-core:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
  maven { url = 'https://jitpack.io' }
}

dependencies {
    implementation("pl.miloszgilga.jmpsl:jmpsl-core:X.Y.Z_AA")
}
```

where `X.Y.Z_AA` is the selected version.

For more info about installation script and integrate with other Java build project
technologies, go to [official Maven repository website](https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core).


<a name="basic-usage"></a>
## Basic usage
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
```
```properties
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
    * `org.springframework.security.core.AuthenticationException`
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

<a name="author"></a>
## Author
Created by Miłosz Gilga. If you have any questions about this application, send message:
[personal@miloszgilga.pl](mailto:personal@miloszgilga.pl).


<a name="license"></a>
## License
This application is on Apache 2.0 License.
