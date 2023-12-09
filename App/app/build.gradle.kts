plugins {
    id("com.android.application")
}

android {
    namespace = "com.invaders.invadersapp"
    compileSdk = 34
    tasks.register("prepareKotlinBuildScriptModel"){}

    defaultConfig {
        applicationId = "com.invaders.invadersapp"
        minSdk = 33
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.metrics:metrics-performance:1.0.0-alpha03")
    androidTestImplementation ("androidx.test:runner:1.4.0'")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0'")
    testImplementation ("org.mockito:mockito-core:3.3.3")
    testImplementation ("androidx.test:core:1.0.0")
    testImplementation ("org.robolectric:robolectric:4.9")
    testImplementation ("androidx.compose.ui:ui-test-junit4:1.5.4")
}