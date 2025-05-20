package plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("org.jetbrains.kotlin.multiplatform")
            plugins.apply("com.android.library")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                iosX64()
                iosArm64()
                iosSimulatorArm64()

                sourceSets.apply {
                    val commonMain = getByName("commonMain")
                    val androidMain = getByName("androidMain")

                    val iosX64Main = getByName("iosX64Main")
                    val iosArm64Main = getByName("iosArm64Main")
                    val iosSimulatorArm64Main = getByName("iosSimulatorArm64Main")

                    val iosMain = maybeCreate("iosMain").apply {
                        dependsOn(commonMain)
                    }

                    iosX64Main.dependsOn(iosMain)
                    iosArm64Main.dependsOn(iosMain)
                    iosSimulatorArm64Main.dependsOn(iosMain)

                    commonMain.dependencies {
                        implementation(libs.findLibrary("kotlinx-coroutines-core").get())
                    }
                    androidMain.dependencies {
                        implementation(libs.findLibrary("ktor-client-okhttp").get())
                    }
                    iosMain.dependencies {
                        implementation(libs.findLibrary("ktor-client-ios").get())
                    }
                }
            }

            extensions.configure<LibraryExtension> {
                namespace = Config.kmpModule
                compileSdk = Config.compileSdk
                defaultConfig {
                    minSdk = Config.minSdkVersion
                }
            }
        }
    }
}
