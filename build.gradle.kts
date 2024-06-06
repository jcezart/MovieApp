// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        // Adicione jcenter() se ainda usar dependências desse repositório, embora esteja sendo descontinuado.
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4") // ou a versão do gradle que você está usando
    }
}