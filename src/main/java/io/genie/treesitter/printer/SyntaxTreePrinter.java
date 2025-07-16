package io.genie.treesitter.printer;

import io.genie.treesitter.TreeCursor;
import io.genie.treesitter.TreeCursorNode;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class SyntaxTreePrinter extends IterativeTreePrinter {

  public SyntaxTreePrinter(@NotNull TreeCursor cursor) {
    super(cursor);
  }

  @Override
  protected String getFileExtension() {
    return ".txt";
  }

  protected void write(Consumer<String> appender) {
    for (; ; ) {
      TreeCursorNode cursorNode = cursor.getCurrentTreeCursorNode();
      if (cursorNode.isNamed()) {
        int depth = cursor.getCurrentDepth();
        String indent = "  ".repeat(depth);
        appender.accept(indent);
        appender.accept(cursorNode.toString());
        appender.accept("\n");
      }
      if (cursor.gotoFirstChild() || cursor.gotoNextSibling()) continue;
      do {
        if (!cursor.gotoParent()) return;
      } while (!cursor.gotoNextSibling());
    }
  }
}
