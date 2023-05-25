# JMPSL File

![](https://img.shields.io/badge/Made%20in-Java%2017%20with%20Gradle-1abc9c.svg)
&nbsp;&nbsp;
![](https://img.shields.io/badge/License-Apache%202.0-brown.svg)

[![][jmpsl file badge]][jmpsl file mvn]
&nbsp;&nbsp;
[![][jmpsl jitpack badge]][jmpsl jitpack]
<br>

[jmpsl jitpack]: https://jitpack.io/#pl.miloszgilga/jmpsl
[jmpsl jitpack badge]: https://img.shields.io/jitpack/version/pl.miloszgilga/jmpsl?color=gree&label=JMPSL%20JitPack

[jmpsl file badge]: https://img.shields.io/maven-central/v/pl.miloszgilga/jmpsl-file?label=JMPSL%20File%20%28Central%29
[jmpsl file mvn]: https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-file

Provide classes for generators, receivers and senders to local or remote FTP server.

Required artifacts: `jmpsl-core`. Corresponding versions.

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
    <artifactId>jmpsl-file</artifactId>
    <version>X.Y.Z_AA</version>
</dependency>
```

* for Gradle project with Groovy:
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'pl.miloszgilga:jmpsl-file:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("pl.miloszgilga:jmpsl-file:X.Y.Z_AA")
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
        <artifactId>jmpsl-file</artifactId>
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
    implementation 'pl.miloszgilga.jmpsl:jmpsl-file:X.Y.Z_AA'
}
```
* for Gradle project with Kotlin:
```kotlin
repositories {
  maven { url = 'https://jitpack.io' }
}

dependencies {
    implementation("pl.miloszgilga.jmpsl:jmpsl-file:X.Y.Z_AA")
}
```

where `X.Y.Z_AA` is the selected version.

For more info about installation script and integrate with other Java build project
technologies, go to [official Maven repository website](https://mvnrepository.com/artifact/pl.miloszgilga/jmpsl-core).


<a name="basic-usage"></a>
## Basic usage

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

<a name="author"></a>
## Author
Created by Miłosz Gilga. If you have any questions about this application, send message:
[personal@miloszgilga.pl](mailto:personal@miloszgilga.pl).


<a name="license"></a>
## License
This application is on MIT License.
