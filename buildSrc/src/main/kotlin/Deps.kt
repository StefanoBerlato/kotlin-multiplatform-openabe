object Versions {
    const val kotlinCoroutines = "1.6.0-native-mt"
    const val kotlin = "1.6.20"
    const val nodePlugin = "1.3.0"
    const val dokkaPlugin = "1.5.0"
    const val taskTreePlugin = "1.5"
    const val jna = "5.10.0"
    const val kodeinVersion = "7.1.0"
    const val resourceLoader = "2.0.2"
}

object ReleaseInfo {
    const val group = "it.stefanoberlato"
    const val bindingsVersion = "0.0.15-SNAPSHOT"
}

object Deps {

    object Common {
        const val stdLib = "stdlib-common"
        const val test = "test-common"
        const val testAnnotation = "test-annotations-common"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
        const val kodein = "org.kodein.di:kodein-di:${Versions.kodeinVersion}"
    }

    object Jvm {
        const val stdLib = "stdlib-jdk8"
        const val test = "test"
        const val testJUnit = "test-junit"
        const val reflection = "reflect"

        const val resourceLoader = "com.goterl:resource-loader:${Versions.resourceLoader}"

        object Delegated {
            const val jna = "net.java.dev.jna:jna:${Versions.jna}"
        }
    }
}

object PluginsDeps {
    const val multiplatform = "multiplatform"
    const val node = "com.github.node-gradle.node"
    const val mavenPublish = "maven-publish"
    const val signing = "signing"
    const val dokka = "org.jetbrains.dokka"
    const val taskTree = "com.dorongold.task-tree"
}

