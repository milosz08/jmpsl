# JMPSL

Java multi purpose Spring library (in short JMPSL) is a is a multi-module development library project for rapidly building
REST-type services in the **Spring Boot framework**. It provides services for authentication, authorization, creating raster
graphics, sending files via FTP and messaging using popular communication protocols, and much more.

<a name="table-of-content"></a>
## Table of content
* [Modules](#modules)
* [Installation](#installation)
* [Versioning strategy](#versioning-strategy)
* [JMPSL Core](#core-module)
* [JMPSL Communication](#module-communication)
* [JMPSL File](#module-file)
* [JMPSL Gfx](#module-gfx)
* [JMPSL Security](#module-security)
* [JMPSL OAuth2](#module-oauth2)
* [Author](#author)
* [Project status](#project-status)
* [License](#license)


<a name="module"></a>
## Modules
Library consists of the following modules:
* `JMPSL Core` - main module: configuration, data access and utilities classes,
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
    <dependency>
        <groupId>pl.miloszgilga.jmpsl</groupId>
        <artifactId>jmpsl-[MOD]</artifactId>
        <version>X.Y.Z_AA</version>
    </dependency>
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
technologies, go to [official Maven repository website](https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core).

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


<a name="core-module"></a>
## JMPSL Core
Main module: configuration, data access and utilities classes.

... in progress
<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="communication-module"></a>
## JMPSL Communication
Provide classes for point-to-point communication (email, websockets etc.),

... in progress
<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="file-module"></a>
## JMPSL File
Provide classes for generators, receivers and senders to local or remove FTP server.

... in progress
<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="gfx-module"></a>
## JMPSL Gfx
Provide classes for 2D raster images generators and manipulators.

... in progress
<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="security-module"></a>
## JMPSL Security
Provide classes which extending Spring Security (for most typical REST API scenarios).

... in progress
<div style="text-align: right">
    [<a href="#table-of-content">return to table of content</a>]
</div>
<hr/>


<a name="oauth2-module"></a>
## JMPSL OAuth2
Provide classes for OAuth2 Spring Security authorization via stateless REST endpoints. At this moment, support following
OAuth2 authentication providers:
* Facebook
* Google
* Github
* LinkedIn

Required artifacts: `jmpsl-core`, `jmpsl-security`. Corresponding versions.<br>
Required spring boot starter: `spring-boot-starter-oauth2-client`. Min version: `3.0.2`

### Basic usage
1. Add Spring Boot starter:
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

2. Apply following properties in `application.properties` or `application.yml`:
```properties


# true, if OAuth2 is active, false if is not active. WARN! If OAuth2 is active, in the absence of any below the required
# property, program will immediately terminate with an error
jmpsl.security.oauth2-active = true

# time (in minutes) after which cookie from external OAuth2 authentication server is expired. Property to required; by
# default is 3 minutes
jmpsl.oauth2.cookie-expired-minutes = 3

# list of all supported suppliers by application. Available options: facebook, google, github, linkedin. Property required.
jmpsl.oauth2.available-suppliers = google,facebook

# redirect URLs after successfull or failure authentication via OAuth2. Separated by comma. Property required.
jmpsl.oauth2.redirect-uris = http://localhost:4200/oauth2/redirect
```
additional you must provide basic Spring Boot OAuth2 configuration.

... in progress

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
