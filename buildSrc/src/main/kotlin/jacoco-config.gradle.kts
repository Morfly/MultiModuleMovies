plugins {
    jacoco
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

private val classDirectoriesTree = fileTree("${project.buildDir}") {
    include(
        "**/classes/**/main/**",
        "**/intermediates/classes/debug/**",
        "**/intermediates/javac/debug/*/classes/**",
        "**/tmp/kotlin-classes/debug/**"
    )
    exclude(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/models/**",
        "**/*Component.*",
        "**/*Module.*",
        "**/*EntryImpl.*",
        "**/ui/**/*.*",
        "**/di/*.*",
    )
}

private val sourceDirectoriesTree = files("$projectDir/src/main/java")

private val executionDataTree = fileTree("${project.buildDir}") {
    include(
        "outputs/code_coverage/**/*.ec",
        "jacoco/jacocoTestReportDebug.exec",
        "jacoco/testDebugUnitTest.exec",
        "jacoco/test.exec"
    )
}

fun JacocoReportsContainer.reports() {
    csv.required.set(false)
    xml.apply {
        required.set(true)
        outputLocation.set(file("$buildDir/reports/code-coverage/xml"))
    }
    html.apply {
        required.set(true)
        outputLocation.set(file("$buildDir/reports/code-coverage/html"))
    }
}

fun JacocoReport.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

fun JacocoCoverageVerification.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

val jacocoGroup = "verification"
tasks.register<JacocoReport>("jacocoTestReport") {
    group = jacocoGroup
    description = "Code coverage report for both Android and Unit tests."
    dependsOn("testDebugUnitTest")
    reports {
        reports()
    }
    setDirectories()
}

val minimumCoverage = "0.8".toBigDecimal()
tasks.register<JacocoCoverageVerification>("jacocoCoverageVerification") {
    group = jacocoGroup
    description = "Code coverage verification for Android both Android and Unit tests."
    dependsOn("testDebugUnitTest")
    violationRules {
        rule {
            limit {
                minimum = minimumCoverage
            }
        }
        rule {
            element = "CLASS"
            excludes = listOf(
                "**.Companion",
            )
            limit {
                minimum = minimumCoverage
            }
        }
    }
    setDirectories()
}
