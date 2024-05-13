plugins {
    id("java")
}

group = "com.fajarmf"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.vincenzopalazzo:material-ui-swing:1.1.2")
    implementation("com.github.jiconfont:jiconfont:1.0.0")
    implementation("com.github.jiconfont:jiconfont-swing:1.0.0")
    implementation("com.jhlabs:filters:2.0.235")
    implementation("org.swinglabs:swingx:1.6.1")
    implementation("org.swinglabs:swing-worker:1.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}



tasks.test {
    useJUnitPlatform()
}


tasks.withType<Jar>() {
    manifest {
        attributes["Main-Class"] = "com.fajarmf.Main"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}
