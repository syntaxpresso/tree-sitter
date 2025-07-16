package io.genie.treesitter.printer;

import io.genie.treesitter.TreeCursor;
import io.genie.treesitter.TreeCursorNode;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SymbolicExpressionPrinter extends IterativeTreePrinter {

  public SymbolicExpressionPrinter(@NotNull TreeCursor cursor) {
    super(cursor);
  }

  @Override
  protected String getFileExtension() {
    return ".scm";
  }

  protected void write(Consumer<String> appender) {
    boolean needsSpace = false;
    boolean visitedChildren = false;
    for (; ; ) {
      TreeCursorNode cursorNode = cursor.getCurrentTreeCursorNode();
      boolean isNamed = cursorNode.isNamed();
      String type = cursorNode.getType();
      String name = cursorNode.getName();
      if (visitedChildren) {
        if (isNamed) {
          appender.accept(")");
          needsSpace = true;
        }
        if (cursor.gotoNextSibling()) visitedChildren = false;
        else if (cursor.gotoParent()) visitedChildren = true;
        else return;
      } else {
        if (isNamed) {
          if (needsSpace) appender.accept(" ");
          if (name != null) {
            appender.accept(name);
            appender.accept(": ");
          }
          appender.accept("(");
          appender.accept(type);
          needsSpace = true;
        }
        visitedChildren = !cursor.gotoFirstChild();
      }
    }
  }
}
