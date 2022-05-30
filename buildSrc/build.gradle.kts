plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    implementation("com.android.tools.build:gradle:4.0.2")
}

System.setProperty("PROJECT_PATH", project.projectDir.parentFile.toString())
println("Path ${project.projectDir.parentFile}")
