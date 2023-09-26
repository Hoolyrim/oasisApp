plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "kr.co.company.vegan"
    compileSdk = 33

    defaultConfig {
        applicationId = "kr.co.company.vegan"
        minSdk = 24
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-tasks:18.0.2")
    implementation("com.google.mlkit:vision-common:17.3.0")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition-common:19.0.0")
    implementation("com.google.firebase:firebase-auth:22.1.2")
    implementation("com.google.firebase:firebase-database:20.2.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.viewpager2:viewpager2:1.0.0") /*viewpager2를 사용하기 위한*/
    // Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")

    // camera
    implementation("androidx.camera:camera-core:1.0.0-rc04") //cranberryai
    implementation("androidx.camera:camera-camera2:1.0.0-rc04") //cranberryai
    implementation("androidx.camera:camera-lifecycle:1.0.0-rc04") //cranberryai
    implementation("androidx.camera:camera-view:1.0.0-alpha23") //cranberryai

    //    implementation ("com.google.android.gms:play-services-mlkit-text-recognition:16.1.3")
    implementation ("com.google.mlkit:text-recognition-korean:16.0.0-beta5")

    // To recognize Korean script
    implementation ("com.google.mlkit:text-recognition-korean:16.0.0")

    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    implementation("com.google.firebase:firebase-analytics-ktx")

//    implementation ("com.kakao.sdk:v2-user:2.8.6") //카카오 로그인
}