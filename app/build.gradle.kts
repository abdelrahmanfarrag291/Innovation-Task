plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.abdelrahman.innovation_task"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.abdelrahman.innovation_task"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    android {
        ndkVersion ="29.0.14206865"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.android.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:presentation"))
    implementation(project(":common-movies:data"))

    implementation(project(":movies-list:data"))
    implementation(project(":movies-list:domain"))
    implementation(project(":movies-list:presentation"))
    implementation(project(":movie-details:data"))
    implementation(project(":movie-details:domain"))
    implementation(project(":movie-details:presentation"))

    implementation(libs.kotlinx.serialization.json)
}