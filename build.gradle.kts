plugins {
    `java-library`
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.16")
    testCompileOnly("org.projectlombok:lombok:1.18.16")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.16")

    testImplementation("junit:junit:4.13.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
    withJavadocJar()
    withSourcesJar()
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
