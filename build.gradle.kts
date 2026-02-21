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
