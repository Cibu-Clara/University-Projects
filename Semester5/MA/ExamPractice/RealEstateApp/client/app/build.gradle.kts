plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.exam"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.exam"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.compose.ui:ui:1.5.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.foundation:foundation:1.5.3")
    implementation("androidx.compose.ui:ui-tooling:1.5.3")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.3")


    implementation ("androidx.navigation:navigation-compose:2.4.0")
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.compose.material:material")

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.activity:activity-compose:1.3.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.3")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //room
    implementation ("androidx.room:room-runtime:2.4.3")
    implementation ("androidx.room:room-ktx:2.4.3")
    implementation ("androidx.compose.runtime:runtime-livedata:1.2.0")
    annotationProcessor ("androidx.room:room-compiler:2.4.3")
    kapt ("androidx.room:room-compiler:2.4.3")
//    viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
//    server
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    web socket
    implementation("tech.gusavila92:java-android-websocket-client:1.2.2")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}