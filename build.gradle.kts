plugins {
    `java-library`
    `maven-publish`
    signing
    id("com.diffplug.spotless") version "6.23.3"
}

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
}

val lombokVersion = "1.18.30"

dependencies {
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
    testCompileOnly("org.projectlombok:lombok:${lombokVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}

tasks.build {
    dependsOn("spotlessApply")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    java {
        importOrder()
        target("**/*.java")
    }

    format("kts") {
        target("**/*.kts")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            group = "net.moznion"
            artifactId = "uribuilder-tiny"
            version = "2.7.2-SNAPSHOT"
            from(components["java"])
            pom {
                name.set("uribuilder-tiny")
                description.set("Minimal and smart URI Builder for Java")
                url.set("https://github.com/moznion/uribuilder-tiny")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/license/mit/")
                    }
                }
                developers {
                    developer {
                        id.set("moznion")
                        name.set("Taiki Kawakami")
                        email.set("moznion@mail.moznion.net")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:moznion/uribuilder-tiny.git")
                    developerConnection.set("scm:git:git@github.com:moznion/uribuilder-tiny.git")
                    url.set("https://github.com/moznion/uribuilder-tiny")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl: String = "https://oss.sonatype.org/service/local/staging/deploy/maven2"
            val snapshotsRepoUrl: String = "https://oss.sonatype.org/content/repositories/snapshots"
            setUrl(uri(if ((version as String).endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl))
            credentials {
                username = fun (): String {
                    val sonatypeUsername = findProperty("sonatypeUsername") ?: return ""
                    return sonatypeUsername as String
                }()
                password = fun (): String {
                    val sonatypePassword = findProperty("sonatypePassword") ?: return ""
                    return sonatypePassword as String
                }()
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}

