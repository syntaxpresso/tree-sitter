package io.genie.treesitter.printer;

import io.genie.treesitter.Point;
import io.genie.treesitter.TreeCursor;
import io.genie.treesitter.TreeCursorNode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class XMLPrinter extends IterativeTreePrinter {

  public static final String PROLOG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  public XMLPrinter(@NotNull TreeCursor cursor) {
    super(cursor);
  }

  @Override
  protected String getPreamble() {
    return PROLOG;
  }

  @Override
  protected String getFileExtension() {
    return ".xml";
  }

  protected void write(Consumer<String> appender) {
    boolean visitedChildren = false;
    Deque<String> tags = new ArrayDeque<>();
    for (; ; ) {
      TreeCursorNode cursorNode = cursor.getCurrentTreeCursorNode();
      boolean isNamed = cursorNode.isNamed();
      String type = cursorNode.getType();
      String name = cursorNode.getName();
      Point start = cursorNode.getStartPoint();
      Point end = cursorNode.getEndPoint();
      if (visitedChildren) {
        if (isNamed) {
          appender.accept("</");
          appender.accept(tags.pop());
          appender.accept(">");
        }
        if (cursor.gotoNextSibling()) visitedChildren = false;
        else if (cursor.gotoParent()) visitedChildren = true;
        else return;
      } else {
        if (isNamed) {
          appender.accept("<");
          appender.accept(type);
          appender.accept(" ");
          if (name != null) {
            appender.accept("name=\"");
            appender.accept(name);
            appender.accept("\" ");
          }
          appender.accept("start=\"");
          appender.accept(start.toString());
          appender.accept("\" ");
          appender.accept("end=\"");
          appender.accept(end.toString());
          appender.accept("\">");
          tags.push(type);
        }
        visitedChildren = !cursor.gotoFirstChild();
      }
    }
  }
}
