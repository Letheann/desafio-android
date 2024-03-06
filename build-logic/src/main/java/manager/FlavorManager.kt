package manager

import com.android.build.gradle.BaseExtension
import org.gradle.api.invocation.Gradle

internal fun BaseExtension.createFlavor() {
    val listAbiAll = listOf("armeabi-v7a", "x86_64", "arm64-v8a", "x86")

    productFlavors {
        create("development") {
            dimension = "type"
            applicationIdSuffix = ".development"
            ndk {
                abiFilters.addAll(listAbiAll)
            }
        }
    }
}

internal fun BaseExtension.createFlavorEmpty() {
    //Note: add in all modules because we need to treatment to take de covarege test e execute test in all project
    productFlavors {
        create("development") { }
    }
}