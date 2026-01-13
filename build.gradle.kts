// Top-level build file where you can add configuration options common to all sub-projects/modules.
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

        if (!System.getProperty("os.name").lowercase().contains("windows")) {
            hookFile.setExecutable(true, false)
        }

        logger.lifecycle("Git hooks installed successfully!")
    }
}

subprojects {
    afterEvaluate {
        tasks.matching { it.name == "preBuild" }.configureEach {
            dependsOn(rootProject.tasks.named("installGitHooks"))
        }
    }
}