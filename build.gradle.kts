/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: build.gradle.kts
 * Last modified: 13/10/2022, 18:31
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL
 * COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 */

plugins {
    id("java-library")
    id("signing")
    id("maven-publish")
    id("co.uzzu.dotenv.gradle") version "2.0.0"
    id("org.springframework.boot") version "2.7.3" apply false
    id("io.spring.dependency-management") version "1.0.13.RELEASE" apply false
}

extra.apply {
    // dependencies version
    set("jTuplesVersion", "1.2")
    set("jjwtVersion", "0.11.5")
    set("springVersion", "2.7.3")
    set("lombokVersion", "1.18.20")
    set("xmlBinderVersion", "2.3.2")
    set("jupiterTestVersion", "5.8.1")
    set("freemarkerVersion", "2.3.31")
    set("orikaMapperVersion", "1.5.4")
    set("javaxPersistenceVersion", "2.2")
    set("reflectionsApiVersion", "0.10.2")
    set("javaxValidationVersion", "2.0.1.Final")

    // maven nexus repository artifacts and additional package information's
    set("libraryVersion", "1.0.2")
    set("globalArtifactId", "pl.miloszgilga")
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "signing")
    apply(plugin = "maven-publish")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    version = rootProject.extra.get("libraryVersion") as String
    group = rootProject.extra.get("globalArtifactId") as String
    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }
    // remove .plain.jar file from repository package
    tasks.jar {
        enabled = true
        archiveClassifier.set("")
    }
    tasks.test {
        useJUnitPlatform()
    }
    java {
        withJavadocJar()
        withSourcesJar()
    }
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.extra.get("globalArtifactId") as String
                artifactId = project.properties["libraryArtifactId"] as String
                version = rootProject.extra.get("libraryVersion") as String
                from(components["java"])
                pom {
                    name.set(project.properties["libraryPomName"] as String)
                    description.set("Java MultiPurpose Spring Library - ${project.properties["libraryPomDesc"] as String}")
                    url.set("https://github.com/Milosz08/jmps-library")
                    inceptionYear.set("2022")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/mit-license.php")
                        }
                    }
                    developers {
                        developer {
                            id.set("milosz08")
                            name.set("Miłosz Gilga")
                            email.set("personal@miloszgilga.pl")
                        }
                    }
                    scm {
                        connection.set("scm:git:git:github.com/Milosz08/jmps-library.git")
                        developerConnection.set("scm:git:ssh://github.com/Milosz08/jmps-library.git")
                        url.set("https://github.com/Milosz08/jmps-library")
                    }
                }
            }
        }
        repositories {
            maven {
                name = "OSSRH"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = env.OSSRH_USERNAME.value
                    password = env.OSSRH_PASSWORD.value
                }
            }
        }
    }
    signing {
        useGpgCmd()
        sign(configurations.archives.get())
    }
    // disable Java DockLint annoying warnings (enable only for checking HTML tags)
    tasks.withType<Javadoc> {
        (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:html", true)
        (options as StandardJavadocDocletOptions).addStringOption("Xmaxwarns", "1")
    }
}

// dependencies only for root project (grabbed all multi-modules into one single project module)
rootProject.dependencies {
    api(project(":jmpsl-util"))
    api(project(":jmpsl-security"))
    api(project(":jmpsl-oauth2"))
    api(project(":jmpsl-communication"))
}

// dependencies for all subprojects
subprojects {
    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra.get("jupiterTestVersion") as String}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra.get("jupiterTestVersion") as String}")
    }
}
