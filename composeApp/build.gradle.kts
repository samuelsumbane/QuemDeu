import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("com.google.accompanist:accompanist-permissions:0.30.1")
            implementation("androidx.datastore:datastore-preferences:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-datastore:1.1.1")
            dependencies { implementation("io.insert-koin:koin-android:3.5.6") }

        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation("cafe.adriel.voyager:voyager-navigator:1.1.0-beta02")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

            // Preferences -------->>
            implementation("com.russhwolf:multiplatform-settings:1.1.1")

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")

            // Koin
            implementation("io.insert-koin:koin-core:3.5.6")        // Core do Koin
            implementation("io.insert-koin:koin-ktor:3.5.6")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.samuelsumbane.quemdeu"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.samuelsumbane.quemdeu"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.samuelsumbane.quemdeu.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.samuelsumbane.quemdeu"
            packageVersion = "1.0.0"
        }
    }
}
