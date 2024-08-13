plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.wstxda.compass"
    compileSdk = 34

    signingConfigs {
        create("release") {
            keyAlias = "CompassKey"
            keyPassword = System.getenv("SIGNING_PASSWORD") ?: ""
            storeFile = file("../keystore.jks")
            storePassword = System.getenv("SIGNING_PASSWORD") ?: ""
        }
    }

    defaultConfig {
        applicationId = "com.wstxda.compass"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 400
        versionName = "4.0"
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
}
