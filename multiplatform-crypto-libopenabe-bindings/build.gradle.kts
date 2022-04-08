import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

val kotlin_version = "1.6.10"

plugins {
    kotlin(PluginsDeps.multiplatform)
    id(PluginsDeps.mavenPublish)
    id(PluginsDeps.signing)
    id(PluginsDeps.node) version Versions.nodePlugin
    id(PluginsDeps.taskTree) version Versions.taskTreePlugin
    id(PluginsDeps.dokka)
}

val sonatypeStaging = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
val sonatypeSnapshots = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
val sonatypePassword: String? by project
val sonatypeUsername: String? by project
val sonatypePasswordEnv: String? = System.getenv()["SONATYPE_PASSWORD"]
val sonatypeUsernameEnv: String? = System.getenv()["SONATYPE_USERNAME"]

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}
group = ReleaseInfo.group
version = ReleaseInfo.bindingsVersion

val ideaActive = isInIdea()
println("Idea active: $ideaActive")


kotlin {
    val hostOsName = getHostOsName()
    jvm()
    val projectRef = project


    runningOnLinuxx86_64 {
        println("Configuring Linux X86-64 targets")

        linuxX64() {
            compilations.getByName("main") {
                val libwrapperCinterop by cinterops.creating {
                    defFile(projectRef.file("src/nativeInterop/cinterop/libwrapper.def"))
                }
            }
            binaries {
                staticLib {
                }
            }
        }
    }

    println(targets.names)







    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin(Deps.Common.stdLib))
                implementation(kotlin(Deps.Common.test))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin(Deps.Common.test))
                implementation(kotlin(Deps.Common.testAnnotation))
                implementation(Deps.Common.coroutines)
            }
        }

        val nativeDependencies = independentDependencyBlock {
        }

        val nativeMain by creating {
            dependsOn(commonMain)
            isRunningInIdea {
                kotlin.setSrcDirs(emptySet<String>())
            }
            dependencies {
                nativeDependencies(this)
            }
        }

        val nativeTest by creating {
            dependsOn(commonTest)
            isRunningInIdea {
                kotlin.setSrcDirs(emptySet<String>())
            }
            dependencies {
            }
        }

        //Set up shared source sets
        //linux
        val linux64Bit = setOf(
            "linuxX64"
        )


        targets.withType<KotlinNativeTarget> {
            println("Target $name")

            compilations.getByName("main") {
                if (linux64Bit.contains(this@withType.name)) {
                    defaultSourceSet.dependsOn(nativeMain)
                }
            }
            compilations.getByName("test") {
                println("Setting native test dep for $this@withType.name")
                defaultSourceSet.dependsOn(nativeTest)
            }
        }

        val jvmMain by getting {
            kotlin.srcDirs("src/jvmSpecific", "src/jvmMain/kotlin")
            dependencies {
                implementation(kotlin(Deps.Jvm.stdLib))
                implementation(kotlin(Deps.Jvm.test))
                implementation(kotlin(Deps.Jvm.testJUnit))

                implementation(Deps.Jvm.resourceLoader)

                implementation(Deps.Jvm.Delegated.jna)

                implementation("org.slf4j:slf4j-api:1.7.30")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin(Deps.Jvm.test))
                implementation(kotlin(Deps.Jvm.testJUnit))
                implementation(kotlin(Deps.Jvm.reflection))
            }
        }
        runningOnLinuxx86_64 {
            println("Configuring Linux 64 Bit source sets")

            val linuxX64Main by getting {
                isRunningInIdea {
                    kotlin.srcDir("src/nativeMain/kotlin")
                }
            }
            val linuxX64Test by getting {
                dependsOn(nativeTest)
                isRunningInIdea {
                    kotlin.srcDir("src/nativeTest/kotlin")
                }
            }
        }

        all {
//            languageSettings.enableLanguageFeature("InlineClasses")
//            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
//            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
        }
    }
}




tasks.whenTaskAdded {
    if("DebugUnitTest" in name || "ReleaseUnitTest" in name) {
        enabled = false // https://youtrack.jetbrains.com/issue/KT-34662 otherwise common tests fail, because we require native android libs to be loaded
    }
}

tasks {
    create<Jar>("javadocJar") {
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaHtml.get().outputDirectory)
    }

    dokkaHtml {
        println("Dokka !")
        dokkaSourceSets {
        }
    }

    if (getHostOsName() == "linux" && getHostArchitecture() == "x86-64") {
        val jvmTest by getting(Test::class) {
            testLogging {
                events("PASSED", "FAILED", "SKIPPED")
                exceptionFormat = TestExceptionFormat.FULL
                showStandardStreams = true
                showStackTraces = true
            }
        }

        val linuxX64Test by getting(KotlinNativeTest::class) {

            testLogging {
                events("PASSED", "FAILED", "SKIPPED")
                exceptionFormat = TestExceptionFormat.FULL
                showStandardStreams = true
                showStackTraces = true
            }
        }
    }
}

allprojects {
    tasks.withType(JavaCompile::class) {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
}

signing {
    isRequired = false
    sign(publishing.publications)
}


publishing {
    publications.withType(MavenPublication::class) {
        artifact(tasks["javadocJar"])
        pom {
            name.set("Kotlin Multiplatform Libopenabe Wrapper")
            description.set("Kotlin Multiplatform Libopenabe Wrapper")
            url.set("https://github.com/StefanoBerlato/kotlin-multiplatform-openabe")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("StefanoBerlato")
                    name.set("Stefano Berlato")
                    email.set("sb.berlatostefano@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/StefanoBerlato/kotlin-multiplatform-openabe")
                connection.set("scm:git:git://git@github.com:StefanoBerlato/kotlin-multiplatform-openabe.git")
                developerConnection.set("scm:git:ssh://git@github.com:StefanoBerlato/kotlin-multiplatform-openabe.git")

            }

        }
    }
    repositories {
        /*maven {
            url = uri(sonatypeStaging)
            credentials {
                username = sonatypeUsername ?: sonatypeUsernameEnv ?: ""
                password = sonatypePassword ?: sonatypePasswordEnv ?: ""
            }
        }*/

        maven {
            name = "snapshot"
            url = uri(sonatypeSnapshots)
            credentials {
                username = sonatypeUsername ?: sonatypeUsernameEnv ?: ""
                password = sonatypePassword ?: sonatypePasswordEnv ?: ""
            }
        }
    }
}
