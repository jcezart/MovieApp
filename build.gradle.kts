// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //alias(libs.plugins.android.application) apply false
    //alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.android.application") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        // Adicione jcenter() se ainda usar dependências desse repositório, embora esteja sendo descontinuado.
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.1") // ou a versão do gradle que você está usando
    }
}