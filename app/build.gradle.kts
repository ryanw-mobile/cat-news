import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android.plugin)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.gradle.ktlint)
}

android {
    namespace = "uk.ryanwong.catnews"

    signingConfigs {
        create("release") {
            val isRunningOnCI = System.getenv("BITRISE") == "true"
            val keystorePropertiesFile = file("../../keystore.properties")

            if (isRunningOnCI || !keystorePropertiesFile.exists()) {
                println("Signing Config: using environment variables")
                keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
                keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")
                storeFile = file(System.getenv("KEYSTORE_LOCATION"))
                storePassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PASSWORD")
            } else {
                println("Signing Config: using keystore properties")
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
        applicationId = "uk.ryanwong.catnews"
        minSdk = libs.versions.minsdk.get().toInt()
        targetSdk = libs.versions.targetsdk.get().toInt()
        versionCode = 3
        versionName = "1.2.0"

        testInstrumentationRunner = "uk.ryanwong.catnews.app.ui.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Bundle output filename
        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
        setProperty("archivesBaseName", "catnews-$versionName-$timestamp")
    }

    buildTypes {
        fun setOutputFileName() {
            applicationVariants.all {
                val variant = this
                variant.outputs
                    .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                    .forEach { output ->
                        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss").format(Date())
                        val outputFileName =
                            "catnews-${variant.versionName}-$timestamp-${variant.name}.apk"
                        output.outputFileName = outputFileName
                    }
            }
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isDebuggable = true
            setOutputFileName()
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
            setOutputFileName()
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
            buildConfigField("String", "DEFAULT_BASE_URL", "\"https://ryanwong.co.uk/restapis/catnews\"")
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
        buildConfig = true
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
    implementation(libs.compose.webview)

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
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    kspAndroidTest(libs.hilt.android.compiler)
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

koverReport {
    // filters for all report types of all build variants
    filters {
        // exclusions for reports
        excludes {
            // excludes class by fully-qualified JVM class name, wildcards '*' and '?' are available
            classes(
                listOf(
                    "uk.ryanwong.catnews.*.Hilt_*",
                    "uk.ryanwong.catnews.*.*_Factory*",
                    "uk.ryanwong.catnews.*.*_HiltModules*",
                    "uk.ryanwong.catnews.*.*Module_*",
                    "uk.ryanwong.catnews.*.*MembersInjector*",
                    "uk.ryanwong.catnews.*.*_Impl*",
                    "uk.ryanwong.catnews.ComposableSingletons*",
                    "uk.ryanwong.catnews.BuildConfig*",
                    "uk.ryanwong.catnews.*.Fake*",
                    "uk.ryanwong.catnews.*.previewparameter*",
                    "uk.ryanwong.catnews.app.ComposableSingletons*",
                    "*Fragment",
                    "*Fragment\$*",
                    "*Activity",
                    "*Activity\$*",
                    "*.BuildConfig",
                    "*.DebugUtil",
                ),
            )
            // excludes all classes located in specified package and it subpackages, wildcards '*' and '?' are available
            packages(
                listOf(
                    "dagger.hilt.internal.aggregatedroot.codegen",
                    "hilt_aggregated_deps",
                    "uk.ryanwong.catnews.app.ui",
                    "uk.ryanwong.catnews.*.ui.screen",
                    "uk.ryanwong.catnews.*.di",
                ),
            )
        }
    }
}
