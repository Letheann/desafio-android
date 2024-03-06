plugins {
    id("applicationPlugin")
    id("composePlugin")
}

android {
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to "*.jar", "dir" to "libs")))

    implementation(libs.support.multDex)
    implementation(libs.bundles.kotlin)
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

    //picasso
    implementation(libs.image.picasso)
}

