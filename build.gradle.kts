plugins {
    `java-library`
    `maven-publish`
}

group = "cloud.aster-lang"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "aster-lang-zh"
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/aster-cloud/${rootProject.name}")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: ""
                password = System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
    }
}

dependencies {
    implementation("cloud.aster-lang:aster-lang-core:0.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.0")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Canonicalizer 内部依赖 en-US 词法表作为翻译目标
    testRuntimeOnly("cloud.aster-lang:aster-lang-en:0.0.1")
}

tasks.test {
    useJUnitPlatform()
}

/**
 * verifyLexiconKeywordParity (P2-R21 audit):
 *   Same intent as the aster-lang-de task: zh-CN.json keyword set must
 *   match the en-US backbone. v2 keyword names (e.g. "或者" instead of
 *   "或") are translation values; the SemanticTokenKind keys must be
 *   identical across all lexicons.
 */
tasks.register("verifyLexiconKeywordParity") {
    group = "verification"
    description = "Ensure zh-CN.json keyword set matches en-US backbone"

    val ours = file("src/main/resources/lexicons/zh-CN.json")
    val backbone = file("../aster-lang-core/src/main/resources/builtin/en-US.json")

    inputs.file(ours)
    inputs.file(backbone)

    doLast {
        if (!backbone.exists()) {
            logger.warn(
                "verifyLexiconKeywordParity: en-US backbone not found at ${backbone.absolutePath}. " +
                    "Sibling aster-lang-core absent — likely non-monorepo CI. Skipping."
            )
            return@doLast
        }
        val parser = groovy.json.JsonSlurper()
        @Suppress("UNCHECKED_CAST")
        val oursKeywords = ((parser.parse(ours) as Map<String, Any>)["keywords"] as Map<String, Any>).keys
        @Suppress("UNCHECKED_CAST")
        val backboneKeywords = ((parser.parse(backbone) as Map<String, Any>)["keywords"] as Map<String, Any>).keys

        val onlyInOurs = oursKeywords - backboneKeywords
        val onlyInBackbone = backboneKeywords - oursKeywords
        if (onlyInOurs.isNotEmpty() || onlyInBackbone.isNotEmpty()) {
            throw GradleException(
                "zh-CN.json keyword drift:\n" +
                    "  only in zh-CN: $onlyInOurs\n" +
                    "  only in en-US: $onlyInBackbone\n" +
                    "Sync the keyword set across all lexicon repos before merging."
            )
        }
        logger.lifecycle(
            "verifyLexiconKeywordParity: zh-CN.json keyword set matches en-US backbone (${oursKeywords.size} keys) ✓"
        )
    }
}

tasks.named("check") {
    dependsOn("verifyLexiconKeywordParity")
}
