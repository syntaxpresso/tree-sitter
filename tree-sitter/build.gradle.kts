tasks.named<DownloadSourceTask>("downloadSource") {
    url = "https://github.com/tree-sitter/tree-sitter/archive/refs/tags/v$version.zip"
}

tasks.named<BuildNativeTask>("buildNative") {
    val downloadSourceTask = tasks.named<DownloadSourceTask>("downloadSource").get()
    additionalCFiles = project.files(downloadSourceTask.srcDir.file("lib/src/lib.c"))
    additionalIncludeDirs = listOf(downloadSourceTask.srcDir.dir("lib/include"), downloadSourceTask.srcDir.dir("lib/src"))
}
