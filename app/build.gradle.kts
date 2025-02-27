import extensions.buildConfigStringField
import extensions.getLocalProperty

plugins {
    id("applicationPlugin")
    id("composePlugin")
}

android {
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    buildTypes.forEach {
        try {
            it.buildConfigStringField("BASE_URL", "https://rest.coinapi.io")
            it.buildConfigStringField("KEY_PUBLIC", getLocalProperty("key.public"))
            it.buildConfigStringField("KEY_PRIVATE", getLocalProperty("key.private"))
        } catch (ignored: Exception) {
            throw InvalidUserDataException("Defina as chaves 'key.public' e" +
                    "'key.private' em local.properties.")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to "*.jar", "dir" to "libs")))

    implementation(libs.support.multDex)
    implementation(libs.bundles.kotlin)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.ui.graphics)
    implementation(libs.compose.toolingpreview)
    implementation(libs.compose.material3)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.manifest)
    ksp(libs.room.compiler)

    // Retrofit
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.core) {
        exclude(group = "okhttp3", module = "okhttp3")
    }
    implementation(libs.retrofit.simpleXML) {
        exclude(group = "xpp3", module = "xpp3")
        exclude(group = "stax", module = "stax-api")
        exclude(group = "stax", module = "stax")
    }
    //Android Support
    implementation(libs.bundles.androidSupport)
    implementation(libs.bundles.androidSupportDesign)

    // ViewModel
    implementation(libs.viewModel.lifecycleExtensions)
    implementation(libs.viewModel.core)

    //koin
    implementation(libs.bundles.koin)

    //room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // encrypted sharedpreferences
    implementation(libs.bundles.sharedpreferences)

    // android tests
    androidTestImplementation(libs.bundles.androidTest)
    testImplementation(libs.bundles.unitTest)
}

