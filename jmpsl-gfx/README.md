# JMPSL GFX
![](https://jitpack.io/v/pl.miloszgilga/jmpsl.svg)
&nbsp;&nbsp;
![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.miloszgilga/jmpsl-gfx/badge.svg)
&nbsp;&nbsp;
![Generic badge](https://img.shields.io/badge/License-MIT-brown.svg)
&nbsp;&nbsp;
![Generic badge](https://img.shields.io/badge/Made%20in-Java%2017%20with%20Gradle-1abc9c.svg)


Provide classes for 2D raster images generators and manipulators.

Required artifacts: `jmpsl-file`, `jmpsl-core`. Corresponding versions.

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
    <artifactId>jmpsl-gfx</artifactId>
    <version>X.Y.Z_AA</version>
</dependency>
```

* for Gradle project with Groovy:
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'pl.miloszgilga:jmpsl-gfx:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("pl.miloszgilga:jmpsl-gfx:X.Y.Z_AA")
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
        <artifactId>jmpsl-gfx</artifactId>
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
    implementation 'pl.miloszgilga.jmpsl:jmpsl-gfx:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
  maven { url = 'https://jitpack.io' }
}

dependencies {
    implementation("pl.miloszgilga.jmpsl:jmpsl-gfx:X.Y.Z_AA")
}
```

where `X.Y.Z_AA` is the selected version.

For more info about installation script and integrate with other Java build project
technologies, go to [official Maven repository website](https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core).


<a name="basic-usage"></a>
## Basic usage
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
        final BufferedImageRes responsePayload = userImageSftpService
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


<a name="author"></a>
## Author
Created by Miłosz Gilga. If you have any questions about this application, send message:
[personal@miloszgilga.pl](mailto:personal@miloszgilga.pl).


<a name="license"></a>
## License
This application is on MIT License.
