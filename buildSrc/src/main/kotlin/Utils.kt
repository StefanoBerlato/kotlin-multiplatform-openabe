import org.gradle.nativeplatform.platform.internal.Architectures
import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun isInIdea() = System.getProperty("idea.active") == "true"

fun getHostOsName(): String {
    val target = System.getProperty("os.name")
    if (target == "Linux") return "linux"
    if (target.startsWith("Windows")) return "windows"
    if (target.startsWith("Mac")) return "macos"
    return "unknown"
}

fun getHostArchitecture(): String {
    val architecture = System.getProperty("os.arch")
    DefaultNativePlatform.getCurrentArchitecture()
    println("Arch: $architecture")
    val resolvedArch = Architectures.forInput(architecture).name
    println("Resolved arch: $resolvedArch")
    return resolvedArch
}

fun KotlinMultiplatformExtension.isRunningInIdea(
    block: KotlinMultiplatformExtension.() -> Unit
) {
    if (isInIdea()) {
        block(this)
    }
}

fun KotlinMultiplatformExtension.runningOnLinuxx86_64(
    block: KotlinMultiplatformExtension.() -> Unit
) {
    if (getHostOsName() == "linux" && getHostArchitecture() == "x86-64") {
        block(this)
    }
}

fun independentDependencyBlock(
    nativeDeps: KotlinDependencyHandler.() -> Unit
): KotlinDependencyHandler.() -> Unit {
    return nativeDeps
}