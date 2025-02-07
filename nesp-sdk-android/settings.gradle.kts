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
pluginManagement {
    repositories {
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/nexus/content/groups/public/")
        maven(url = "https://maven.aliyun.com/nexus/content/repositories/jcenter")
        maven(url = "https://jitpack.io")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.PREFER_SETTINGS
    repositories {
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/nexus/content/groups/public/")
        maven(url = "https://maven.aliyun.com/nexus/content/repositories/jcenter")
        maven(url = "https://jitpack.io")
        google()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("com.android.library") version "8.8.0" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
}

rootProject.name = "nesp-sdk-android"

// include(":NespRecyclerView")
// project(":NespRecyclerView").projectDir = file("../NespRecyclerView/NespRecyclerView")

include(":alipay-sdk-aar")
include(":compressor-aar")
include(":trpay-sdk-aar")

includeBuild("../nesp-sdk-java")
includeBuild("../nesp-sdk-kotlin")
includeBuild("../NespRecyclerView")

// includeBuild("../nesp-sdk-java/nesp-sdk-java"){
//     dependencySubstitution {
//         substitute(module("com.nesp.sdk.java:nesp-sdk-java")).using(project(":"))
//     }
// }
//
// includeBuild("../nesp-sdk-kotlin/nesp-sdk-kotlin") {
//     dependencySubstitution {
//         substitute(module("com.nesp.sdk.kotlin:nesp-sdk-kotlin")).using(project(":"))
//     }
// }

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    nesp-sdk-kotlin requires JDK 17+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}
