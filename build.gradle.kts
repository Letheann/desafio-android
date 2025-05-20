@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

allprojects{
    configurations.all {
        resolutionStrategy.force(libs.okHttp3.core)
        resolutionStrategy.force(libs.okHttp3.core)
        resolutionStrategy.force(libs.test.objenesis)
        resolutionStrategy.force(libs.image.picasso)
    }
}

tasks.register("clean").configure {
    delete("build")
}
