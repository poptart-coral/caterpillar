plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.spotless) apply false
}
tasks.register<Copy>("copyGitHooks") {
    description = "Copies the git hooks from scripts/ to .git/hooks/"
    group = "git hooks"
    from("$rootDir/scripts/pre-commit")
    into("$rootDir/.git/hooks/")
}

tasks.register("installGitHooks") {
    description = "Installs the pre-commit git hook"
    group = "git hooks"

    dependsOn("copyGitHooks")

    onlyIf {
        val hookFile = file("$rootDir/.git/hooks/pre-commit")
        val sourceFile = file("$rootDir/scripts/pre-commit")
        !hookFile.exists() || hookFile.readText() != sourceFile.readText()
    }

    doLast {
        val hookFile = file("$rootDir/.git/hooks/pre-commit")

        // Check the file exist
        if (!hookFile.exists()) {
            logger.error("Hook file not found after copy")
            throw GradleException("Failed to copy pre-commit hook")
        }

        // Set executable
        val success = hookFile.setExecutable(true, false)

        if (!success) {
            logger.warn("Could not set executable via Gradle, trying chmod...")

            exec {
                commandLine("chmod", "+x", hookFile.absolutePath)
                isIgnoreExitValue = true
            }
        }

        // Check it is executable
        if (hookFile.canExecute()) {
            logger.lifecycle("Git hooks installed successfully!")
        } else {
            logger.error("Hook installed but not executable. Run: chmod +x .git/hooks/pre-commit")
        }
    }
}

subprojects {
    afterEvaluate {
        tasks.matching { it.name == "preBuild" }.configureEach {
            dependsOn(rootProject.tasks.named("installGitHooks"))
        }
    }
}