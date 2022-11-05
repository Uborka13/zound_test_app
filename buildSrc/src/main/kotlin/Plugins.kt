object Plugins {
    const val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Hilt.core}"
    const val mapSecretPlugin =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${
        Versions.mapsPlatformSecretPlugin
        }"
    const val gradleCheckDependencyPlugin =
        "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleDependencyCheckPlugin}"

    const val kotlinSerialization =
        "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinPlugin}"

    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val dependencyVersionChecker = "com.github.ben-manes:gradle-versions-plugin:${Versions.dependencyVersionChecker}"
}
