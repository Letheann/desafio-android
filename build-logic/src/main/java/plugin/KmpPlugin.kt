package plugin

import Config
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KmpPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            plugins.apply("org.jetbrains.kotlin.multiplatform")
            plugins.apply("com.android.library")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget {
                    compilations.all {
                        kotlinOptions {
                            jvmTarget = "17"
                        }
                    }
                }
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
                        implementation(
                            libs.findLibrary("ktor_serialization_kotlinx_json").orElseThrow()
                        )
                        implementation(libs.findLibrary("ktor-client-core").orElseThrow())
                        implementation(
                            libs.findLibrary("ktor-client-content-negotiation").orElseThrow()
                        )
                        implementation(libs.findLibrary("kotlinx-serialization-json").orElseThrow())
                        implementation(libs.findLibrary("kotlinx-coroutines-core").orElseThrow())
                    }

                    androidMain.dependencies {
                        implementation(libs.findLibrary("ktor-client-okhttp").orElseThrow())
                        implementation(libs.findBundle("koin").orElseThrow())
                    }

                    iosMain.dependencies {
                        implementation("io.ktor:ktor-client-darwin:2.3.5")
                    }
                }
            }

            extensions.configure<LibraryExtension> {
                namespace = Config.kmpModule
                compileSdk = Config.compileSdk

                defaultConfig {
                    minSdk = Config.minSdkVersion
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
            }

            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    jvmTarget = "17"
                }
            }
        }
    }
}
