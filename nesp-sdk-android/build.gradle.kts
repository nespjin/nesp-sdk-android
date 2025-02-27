import com.android.build.gradle.internal.tasks.factory.dependsOn

/*
 * Copyright (C) 2021 The NESP Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("maven-publish")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

val artifactGroup = "com.nesp.sdk.android"
val artifactVersion = "1.0.0"

group = artifactGroup
version = artifactVersion

android {
    compileSdk = 33

    defaultConfig {
        namespace = "com.nesp.sdk.android"

        minSdk = 21
        lint.targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    packaging {
        resources {
            pickFirsts += listOf( "**" )
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// parent?.allprojects {
//     repositories {
//         mavenLocal()
//     }
// }

publishing {
    publications {
        register<MavenPublication>("alipay-sdk-aar") {
            groupId = "aar.deps"
            artifactId = "alipay-sdk-aar"
            version = "1.0.0"
            artifact("libs/alipaySdk-15.6.5-20190718211159-noUtdid.aar")
        }

        register<MavenPublication>("compressor-aar") {
            groupId = "aar.deps"
            artifactId = "compressor-aar"
            version = "1.0.0"
            artifact("libs/compressor-2.1.0.aar")
        }

        register<MavenPublication>("trpay-sdk-aar") {
            groupId = "aar.deps"
            artifactId = "trpay-sdk-aar"
            version = "1.0.0"
            artifact("libs/paysdk-release-1.2.5.aar")
        }
    }
}

// add the publication before the build even starts
// used ./gradlew mymodule:assemble --dry-run to find where to put it
afterEvaluate {
  tasks.clean.dependsOn("publishToMavenLocal")
  tasks.preBuild.dependsOn("publishToMavenLocal")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-fragment:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui:2.3.5")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("androidx.localbroadcastmanager:localbroadcastmanager:1.0.0")

    val activityVersion = "1.4.0"
    implementation("androidx.activity:activity-ktx:$activityVersion")
    implementation("androidx.activity:activity-ktx:$activityVersion")

    val fragmentVersion = "1.4.0"
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    implementation("androidx.fragment:fragment-ktx:$fragmentVersion")
    androidTestImplementation("androidx.fragment:fragment-testing:$fragmentVersion")

    val lifecycleVersion = "2.4.0"
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    val navVersion = "2.3.5"
    // Java language implementation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-fragment:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui:$navVersion")
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")
    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:2.4.0-beta02")

    val workVersion = "2.5.0"

    // (Java only)
    implementation("androidx.work:work-runtime:$workVersion")

    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:$workVersion")

    // optional - RxJava2 support
    implementation("androidx.work:work-rxjava2:$workVersion")

    // optional - GCMNetworkManager support
    implementation("androidx.work:work-gcm:$workVersion")

    // optional - Test helpers
    androidTestImplementation("androidx.work:work-testing:$workVersion")

    // optional - Multiprocess support
    implementation("androidx.work:work-multiprocess:$workVersion")

    ///////////////////////////////////////////////////
    /// Nesp SDK
    ///////////////////////////////////////////////////
    // implementation(project(":NespRecyclerView"))
    implementation("com.nesp.sdk.android.widget:NespRecyclerView")
    implementation("com.nesp.sdk.java:nesp-sdk-java")
    implementation("com.nesp.sdk.kotlin:nesp-sdk-kotlin")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.github.goweii:SwipeBack:1.1.0")
    implementation("com.github.goweii:Blurred:1.3.0")
    implementation("com.github.mmin18:realtimeblurview:1.2.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("com.google.guava:guava:24.1-jre")
    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
    implementation("gdut.bsx:share2:0.9.3")

    // Old SDK
    // 添加layoutlib.jar
    compileOnly(files(android.sdkDirectory.path + "/platforms/android-25/data/layoutlib.jar"))

    // org.apache.commons
    // implementation ("org.apache.commons:commons-lang3:3.12.0")
    // implementation ("org.apache.commons:commons-collections4:4.4")
    // implementation ("org.apache.commons:commons-text:1.9")
    // implementation ("org.apache.commons:commons-compress:1.21")
    // implementation ("org.apache.commons:commons-math3:3.6.1")
    // implementation ("org.apache.commons:commons-pool2:2.11.1")
    implementation("org.apache.commons:commons-io:1.3.2")
    // implementation ("org.apache.commons:commons-csv:1.9.0")
    // implementation ("org.apache.commons:commons-configuration2:2.7")
    // implementation ("org.apache.commons:commons-exec:1.3")
    // implementation ("org.apache.commons:commons-crypto:1.1.0")
    // implementation ("org.apache.commons:commons-jexl3:3.2.1")
    // implementation ("org.apache.commons:commons-vfs2:2.9.0")
    // implementation ("org.apache.commons:commons-email:1.5")
    implementation("commons-codec:commons-codec:1.15")

    // ZXing
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.zxing:android-core:3.3.0")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    // Compressor
    // compileOnly(files("libs/compressor-2.1.0.aar"))
    // implementation(project(":compressor-aar"))
    implementation("aar.deps:compressor-aar:1.0.0")
    // HokoBlur
    api("com.hoko:hoko-blur:1.3.3")
    api(files("libs/open_sdk_r2973327_lite.jar"))
    // compileOnly(files("libs/paysdk-release-1.2.5.aar"))
    // implementation(project(":trpay-sdk-aar"))
    implementation("aar.deps:trpay-sdk-aar:1.0.0")
    implementation("com.tencent.mm.opensdk:wechat-sdk-android-without-mta:6.7.9")

    // paysdk-release-1.2.1(sdk名称)
    // compileOnly(files("libs/alipaySdk-15.6.5-20190718211159-noUtdid.aar"))
    // implementation(project(":alipay-sdk-aar"))
    implementation("aar.deps:alipay-sdk-aar:1.0.0")
    implementation("com.alibaba:fastjson:1.2.79")

    // 今日头条适配框架
    // implementation("me.jessyan:autosize:1.1.2")
    //
    // QMUI
    // implementation("com.qmuiteam:qmui:1.1.3")

    implementation("org.greenrobot:eventbus:3.3.1")

    implementation("com.arialyy.aria:core:3.8.6")
    annotationProcessor("com.arialyy.aria:compiler:3.8.6")
    implementation("com.arialyy.aria:m3u8Component:3.8.6")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    implementation("com.github.lany192:DateTimePicker:1.0.3")
    // implementation ("com.github.lany192:NumberPicker:1.0.3")
    implementation("com.contrarywind:Android-PickerView:4.1.9")

    // implementation ("com.github.microshow:RxFFmpeg:2.2.0")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
}

/*

apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-kapt'
apply plugin: 'kotlin-parcelize'

android {
    compileSdkVersion 31
//    buildToolsVersion "30.0.3"

    defaultConfig {
        if (project.android.hasProperty("namespace")) {
            namespace 'com.nesp.sdk.android'
        }
        minSdkVersion 21
        targetSdkVersion 31

        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles "consumer-rules.pro"
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }


    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    buildFeatures {
        viewBinding true
        dataBinding true
    }
}

dependencies {
    implementation fileTree(dir: "libs", include: ["*.jar"], excludes: [""])
    implementation "androidx.core:core-ktx:1.7.0"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
    implementation 'androidx.appcompat:appcompat:1.4.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.2'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'
    implementation 'androidx.navigation:navigation-fragment:2.3.5'
    implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'
    implementation 'androidx.navigation:navigation-ui:2.3.5'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.5'
    implementation 'androidx.navigation:navigation-ui-ktx:2.3.5'
    implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'

    def activity_version = '1.4.0'
    implementation "androidx.activity:activity-ktx:$activity_version"
    implementation "androidx.activity:activity-ktx:$activity_version"

    def fragment_version = '1.4.0'
    implementation "androidx.fragment:fragment-ktx:$fragment_version"
    implementation "androidx.fragment:fragment-ktx:$fragment_version"
    androidTestImplementation "androidx.fragment:fragment-testing:$fragment_version"

    def lifecycle_version = '2.4.0'
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"

    def nav_version = "2.3.5"
    // Java language implementation
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-fragment:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui:$nav_version"
    // Kotlin
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
    // Feature module Support
    implementation "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"
    // Testing Navigation
    androidTestImplementation "androidx.navigation:navigation-testing:$nav_version"
    // Jetpack Compose Integration
    implementation 'androidx.navigation:navigation-compose:2.4.0-beta02'

    def work_version = "2.5.0"

    // (Java only)
    implementation "androidx.work:work-runtime:$work_version"

    // Kotlin + coroutines
    implementation "androidx.work:work-runtime-ktx:$work_version"

    // optional - RxJava2 support
    implementation "androidx.work:work-rxjava2:$work_version"

    // optional - GCMNetworkManager support
    implementation "androidx.work:work-gcm:$work_version"

    // optional - Test helpers
    androidTestImplementation "androidx.work:work-testing:$work_version"

    // optional - Multiprocess support
    implementation "androidx.work:work-multiprocess:$work_version"

    ///////////////////////////////////////////////////
    /// Nesp SDK
    ///////////////////////////////////////////////////
//    implementation project(':NespRecyclerView')
    implementation 'com.nesp.sdk.java:nesp-sdk-java'
    implementation 'com.nesp.sdk.kotlin:nesp-sdk-kotlin'

    implementation 'com.github.bumptech.glide:glide:4.12.0'
    implementation 'com.github.goweii:SwipeBack:1.1.0'
    implementation 'com.github.goweii:Blurred:1.3.0'
    implementation 'com.github.mmin18:realtimeblurview:1.2.1'

    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    implementation 'com.google.guava:guava:24.1-jre'
    implementation 'com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava'
    implementation 'gdut.bsx:share2:0.9.3'

    // Old SDK
    // 添加layoutlib.jar
    compileOnly files(getLayoutLibFilePath())

    // org.apache.commons
//    implementation 'org.apache.commons:commons-lang3:3.12.0'
//    implementation 'org.apache.commons:commons-collections4:4.4'
//    implementation 'org.apache.commons:commons-text:1.9'
//    implementation 'org.apache.commons:commons-compress:1.21'
//    implementation 'org.apache.commons:commons-math3:3.6.1'
//    implementation 'org.apache.commons:commons-pool2:2.11.1'
    implementation 'org.apache.commons:commons-io:1.3.2'
//    implementation 'org.apache.commons:commons-csv:1.9.0'
//    implementation 'org.apache.commons:commons-configuration2:2.7'
//    implementation 'org.apache.commons:commons-exec:1.3'
//    implementation 'org.apache.commons:commons-crypto:1.1.0'
//    implementation 'org.apache.commons:commons-jexl3:3.2.1'
//    implementation 'org.apache.commons:commons-vfs2:2.9.0'
//    implementation 'org.apache.commons:commons-email:1.5'
    implementation 'commons-codec:commons-codec:1.15'

    // ZXing
    implementation 'com.google.zxing:core:3.4.1'
    implementation 'com.google.zxing:android-core:3.3.0'
    // Glide
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    // Compressor
    implementation files('libs/compressor-2.1.0.aar')
    //HokoBlur
    api 'com.hoko:hoko-blur:1.3.3'
    api files('libs/open_sdk_r2973327_lite.jar')
    api files('libs/paysdk-release-1.2.5.aar')
    implementation 'com.tencent.mm.opensdk:wechat-sdk-android-without-mta:6.7.9'

    // paysdk-release-1.2.1(sdk名称)
    implementation files('libs/alipaySdk-15.6.5-20190718211159-noUtdid.aar')
    implementation 'com.alibaba:fastjson:1.2.78'

    //今日头条适配框架
    //    implementation 'me.jessyan:autosize:1.1.2'

    //    QMUI
    //    implementation 'com.qmuiteam:qmui:1.1.3'

    implementation 'org.greenrobot:eventbus:3.3.1'

    implementation 'com.arialyy.aria:core:3.8.6'
    annotationProcessor 'com.arialyy.aria:compiler:3.8.6'
    implementation 'com.arialyy.aria:m3u8Component:3.8.6'
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01'

    implementation 'com.github.lany192:DateTimePicker:1.0.3'
//    implementation 'com.github.lany192:NumberPicker:1.0.3'
    implementation 'com.contrarywind:Android-PickerView:4.1.9'

    //    implementation 'com.github.microshow:RxFFmpeg:2.2.0'
}

private String getLayoutLibFilePath() {
    return Paths.get(android.getSdkDirectory().getAbsolutePath(),"platforms","android-25","data","layoutlib.jar").toString()
}

repositories {
    maven { url 'https://maven.aliyun.com/nexus/content/groups/public/' }
    maven { url 'https://maven.aliyun.com/nexus/content/repositories/jcenter' }
    maven { url 'https://maven.aliyun.com/repository/google' }
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    maven { url "https://jitpack.io" }
    mavenCentral()
    google()
    jcenter()
}*/
