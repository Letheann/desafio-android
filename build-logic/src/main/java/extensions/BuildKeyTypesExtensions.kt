package extensions

import com.android.build.gradle.internal.dsl.BuildType
import java.io.File
import java.util.Properties

fun BuildType.buildConfigStringField(name: String, value: String) {
    this.buildConfigField("String", name, "\"$value\"")
}

fun getLocalProperty(key: String, defaultValue: String = ""): String {
    val properties = Properties()
    val file = File("local.properties")
    if (file.exists()) {
        properties.load(file.inputStream())
    }
    return properties.getProperty(key, defaultValue)
}