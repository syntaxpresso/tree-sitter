tasks.named<DownloadSourceTask>("downloadSource") {
    url = "https://github.com/tree-sitter/tree-sitter-java/archive/refs/tags/v$version.zip"
}

tasks.named<BuildNativeTask>("buildNative") {
    additionalIncludeDirs.add(
        tasks
            .named("downloadSource", DownloadSourceTask::class.java)
            .get()
            .srcDir
            .dir("src"),
    )
}

java {
    withJavadocJar()
    withSourcesJar()
}
