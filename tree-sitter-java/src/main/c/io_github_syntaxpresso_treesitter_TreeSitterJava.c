
#include <jni.h>
void *tree_sitter_java();
/*
 * Class:     io_github_syntaxpresso_treesitter_TreeSitterJava
 * Method:    tree_sitter_java
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_io_github_syntaxpresso_treesitter_TreeSitterJava_tree_1sitter_1java
  (JNIEnv *env, jclass clz){
   return (jlong) tree_sitter_java();
}
