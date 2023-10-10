import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android.plugin)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "uk.ryanwong.skycatnews"

    signingConfigs {
        create("release") {
            val isRunningOnBitrise = System.getenv("BITRISE") == "true"
            val keystorePropertiesFile = file("../../keystore.properties")

            if (isRunningOnBitrise || !keystorePropertiesFile.exists()) {
                keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
                keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
                storeFile = file(System.getenv("HOME") + "/keystores/release.jks")
                storePassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PASSWORD")
            } else {
                val properties = Properties()
                InputStreamReader(
                    FileInputStream(keystorePropertiesFile),
                    Charsets.UTF_8,
                ).use { reader ->
                    properties.load(reader)
                }

                keyAlias = properties.getProperty("alias")
                keyPassword = properties.getProperty("pass")
                storeFile = file(properties.getProperty("store"))
                storePassword = properties.getProperty("storePass")
            }
        }
    }

    compileSdk = libs.versions.compilesdk.get().toInt()
    defaultConfig {
        applicationId = "uk.ryanwong.skycatnews"
        minSdk = libs.versions.minsdk.get().toInt()
        targetSdk = libs.versions.targetsdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "uk.ryanwong.skycatnews.app.ui.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Bundle output filename
        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
        setProperty("archivesBaseName", "skycatnews-$versionName-$timestamp")
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isDebuggable = true

            applicationVariants.all {
                val variant = this
                variant.outputs
                    .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach { output ->
                        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
                        val outputFileName =
                            "skycatnews-${variant.name}-${variant.versionName}-$timestamp.apk"
                        output.outputFileName = outputFileName
                    }
            }
        }

        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            isDebuggable = false

            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                ),
            )

            signingConfig = signingConfigs.getByName("release")
            applicationVariants.all {
                val variant = this
                variant.outputs
                    .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach { output ->
                        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
                        val outputFileName =
                            "skycatnews-${variant.name}-${variant.versionName}-$timestamp.apk"
                        output.outputFileName = outputFileName
                    }
            }
        }
    }

    flavorDimensions.add("datasource")
    productFlavors {
        create("fake") {
            dimension = "datasource"
            buildConfigField("String", "DEFAULT_BASE_URL", "\"\"")
        }
        create("prod") {
            dimension = "datasource"
            buildConfigField("String", "DEFAULT_BASE_URL", "\"https://ryanwong.co.uk/restapis\"")
        }
    }
    packaging {
        resources {
            excludes.addAll(
                listOf(
                    "META-INF/proguard/*",
                    "META-INF/*.kotlin_module",
                    "META-INF/DEPENDENCIES",
                    "META-INF/AL2.0",
                    "META-INF/LGPL2.1",
                    "META-INF/*.properties",
                    "/*.properties",
                    "META-INF/LICENSE*",
                ),
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.kotlin.compiler.extension.get()
    }
    testOptions {
        animationsDisabled = true

        unitTests {
            isIncludeAndroidResources = true
        }
    }
    // https://kotlinlang.org/docs/gradle-configure-project.html#gradle-java-toolchains-support
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlin.reflect)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.timber)
    debugImplementation(libs.leakcanary.android)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.coroutines)
    implementation(libs.accompanist.webview)

    // compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit)
    testImplementation(libs.bundles.kotest)
    with(libs.androidx.test) {
        androidTestImplementation(junit4)
        androidTestImplementation(rules)
        androidTestImplementation(espresso.core)
        androidTestImplementation(espresso.idling.resource)
        androidTestImplementation(libs.kotest.assertions.core)
    }

    // Dagger-Hilt
    // Hilt does not support ksp yet https://issuetracker.google.com/issues/179057202?pli=1
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)

    // Ktor
    implementation(libs.bundles.ktor)
    testImplementation(libs.ktor.client.mock)
    implementation(libs.kotlinx.serialization.json)

    // RoomDB
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)

    // Mockk
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.agent.jvm)
    androidTestImplementation(libs.mockk.android)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    android.set(true)
    ignoreFailures.set(true)
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.SARIF)
    }
}

tasks.named("preBuild") {
    dependsOn(tasks.named("ktlintFormat"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}

/*
 * Kover configs
 */
koverReport {
    // filters for all report types of all build variants
    filters {
        excludes {
            classes(
                "dagger.hilt.internal.aggregatedroot.codegen.*",
                "hilt_aggregated_deps.*",
                "uk.ryanwong.skycatnews.app.ui.*",
                "uk.ryanwong.skycatnews.*.ui.screen.*",
                "uk.ryanwong.skycatnews.*.di.*",
                "uk.ryanwong.skycatnews.*.Hilt_*",
                "uk.ryanwong.skycatnews.*.*_Factory*",
                "uk.ryanwong.skycatnews.*.*_HiltModules*",
                "uk.ryanwong.skycatnews.*.*Module_*",
                "uk.ryanwong.skycatnews.*.*MembersInjector*",
                "uk.ryanwong.skycatnews.*.*_Impl*",
                "uk.ryanwong.skycatnews.ComposableSingletons*",
                "uk.ryanwong.skycatnews.BuildConfig*",
                "uk.ryanwong.skycatnews.*.Fake*",
                "uk.ryanwong.skycatnews.*.previewparameter*",
                "uk.ryanwong.skycatnews.app.ComposableSingletons*",
            )
        }
    }

    androidReports("fakeRelease") {
        // filters for all report types only of 'release' build type
        filters {
            excludes {
                classes(
                    "*Fragment",
                    "*Fragment\$*",
                    "*Activity",
                    "*Activity\$*",
                    "*.databinding.*",
                    "*.BuildConfig",

                    // excludes debug classes
                    "*.DebugUtil",
                )
            }
        }
    }
}
