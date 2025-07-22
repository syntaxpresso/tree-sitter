package io.github.syntaxpresso.treesitter;

import io.github.syntaxpresso.treesitter.utils.NativeUtils;

public class TreeSitterJava extends TSLanguage {

  static {
    NativeUtils.loadLib("lib/tree-sitter-java");
  }

  private static native long tree_sitter_java();

  public TreeSitterJava() {
    super(tree_sitter_java());
  }

  private TreeSitterJava(long ptr) {
    super(ptr);
  }

  @Override
  public TSLanguage copy() {
    return new TreeSitterJava(copyPtr());
  }
}
