package io.genie.treesitter;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OffsetTreeCursor extends TreeCursor.Stub {

  private static final String UOE_MESSAGE_1 =
      "Byte positions not available after node position has changed!";
  private static final String UOE_MESSAGE_2 =
      "Byte position searches not supported after node position has changed!";
  private static final String UOE_MESSAGE_3 =
      "Querying not available after node position has changed!";
  private static final String UOE_MESSAGE_4 = "Byte positions not available!";

  TreeCursor cursor;
  Point offset;

  public OffsetTreeCursor(@NotNull Node node, @NotNull Point offset) {
    Objects.requireNonNull(node, "Node must not be null!");
    Objects.requireNonNull(offset, "Offset must not be null!");
    this.cursor = node.walk();
    this.offset = offset;
  }

  @Override
  public void close() {
    cursor.close();
  }

  @Override
  public int getCurrentDepth() {
    return cursor.getCurrentDepth();
  }

  @Override
  public String getCurrentFieldName() {
    return cursor.getCurrentFieldName();
  }

  @Override
  public boolean gotoFirstChild() {
    return cursor.gotoFirstChild();
  }

  @Override
  public boolean gotoFirstChild(int offset) {
    throw new UnsupportedOperationException(UOE_MESSAGE_2);
  }

  @Override
  public boolean gotoFirstChild(@NotNull Point point) {
    return cursor.gotoFirstChild(point.subtract(offset));
  }

  @Override
  public boolean gotoLastChild() {
    return cursor.gotoLastChild();
  }

  @Override
  public boolean gotoNextSibling() {
    return cursor.gotoNextSibling();
  }

  @Override
  public boolean gotoPrevSibling() {
    return cursor.gotoPrevSibling();
  }

  @Override
  public boolean gotoParent() {
    return cursor.gotoParent();
  }

  @Override
  public boolean gotoNode(@NotNull Node node) {
    return cursor.gotoNode(node);
  }

  @Override
  public void preorderTraversal(@NotNull Consumer<Node> callback) {
    cursor.preorderTraversal(callback);
  }

  @Override
  public boolean reset(@NotNull TreeCursor other) {
    return cursor.reset(other);
  }

  @Override
  public Node getCurrentNode() {
    return new OffsetNode(cursor.getCurrentNode());
  }

  @Override
  public TreeCursor clone() {
    return new OffsetTreeCursor(cursor.getCurrentNode(), offset);
  }

  @Override
  public String toString() {
    return String.format(
        "OffsetTreeCursor(row: %d, column: %d)", offset.getRow(), offset.getColumn());
  }

  @AllArgsConstructor(access = AccessLevel.PACKAGE)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  private class OffsetNode extends Node.Null {

    Node node;

    @Override
    public Node getChild(int child) {
      return new OffsetNode(node.getChild(child));
    }

    @Override
    public Node getChildByFieldName(@NotNull String name) {
      return new OffsetNode(node.getChildByFieldName(name));
    }

    @Override
    public int getChildCount() {
      return node.getChildCount();
    }

    @Override
    public List<Node> getChildren() {
      return node.getChildren().stream()
          .map(OffsetNode::new)
          .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String getContent() {
      return node.getContent();
    }

    @Override
    public Node getDescendant(int startByte, int endByte) {
      throw new UnsupportedOperationException(UOE_MESSAGE_2);
    }

    @Override
    public Node getDescendant(@NotNull Point startPoint, @NotNull Point endPoint) {
      return node.getDescendant(startPoint.subtract(offset), endPoint.subtract(offset));
    }

    @Override
    public int getDescendantCount() {
      return node.getDescendantCount();
    }

    @Override
    public int getEndByte() {
      throw new UnsupportedOperationException(UOE_MESSAGE_1);
    }

    @Override
    public Point getEndPoint() {
      return node.getEndPoint().add(offset);
    }

    @Override
    public String getFieldNameForChild(int child) {
      return node.getFieldNameForChild(child);
    }

    @Override
    public Symbol getGrammarSymbol() {
      return node.getGrammarSymbol();
    }

    @Override
    public String getGrammarType() {
      return node.getGrammarType();
    }

    @Override
    public Node getFirstChildForByte(int offset) {
      throw new UnsupportedOperationException(UOE_MESSAGE_2);
    }

    @Override
    public Node getFirstNamedChildForByte(int offset) {
      throw new UnsupportedOperationException(UOE_MESSAGE_2);
    }

    @Override
    public Language getLanguage() {
      return node.getLanguage();
    }

    @Override
    public Node getNamedChild(int child) {
      return new OffsetNode(node.getNamedChild(child));
    }

    @Override
    public int getNamedChildCount() {
      return node.getNamedChildCount();
    }

    @Override
    public List<Node> getNamedChildren() {
      return node.getNamedChildren().stream()
          .map(OffsetNode::new)
          .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Node getNamedDescendant(int startByte, int endByte) {
      throw new UnsupportedOperationException(UOE_MESSAGE_2);
    }

    @Override
    public Node getNamedDescendant(@NotNull Point startPoint, @NotNull Point endPoint) {
      return node.getNamedDescendant(startPoint.subtract(offset), endPoint.subtract(offset));
    }

    @Override
    public Node getNextNamedSibling() {
      return new OffsetNode(node.getNextNamedSibling());
    }

    @Override
    public int getNextParseState() {
      return node.getNextParseState();
    }

    @Override
    public Node getNextSibling() {
      return new OffsetNode(node.getNextSibling());
    }

    @Override
    public Node getPrevNamedSibling() {
      return new OffsetNode(node.getPrevNamedSibling());
    }

    @Override
    public Node getPrevSibling() {
      return new OffsetNode(node.getPrevSibling());
    }

    @Override
    public Node getParent() {
      return new OffsetNode(node.getParent());
    }

    @Override
    public int getParseState() {
      return node.getParseState();
    }

    @Override
    public Range getRange() {
      return new PositionOnlyRange(this);
    }

    @Override
    public int getStartByte() {
      throw new UnsupportedOperationException(UOE_MESSAGE_1);
    }

    @Override
    public Point getStartPoint() {
      return node.getStartPoint().add(offset);
    }

    @Override
    public Symbol getSymbol() {
      return node.getSymbol();
    }

    @Override
    public String getType() {
      return node.getType();
    }

    @Override
    public boolean hasChanges() {
      return node.hasChanges();
    }

    @Override
    public boolean hasError() {
      return node.hasError();
    }

    @Override
    public boolean isError() {
      return node.isError();
    }

    @Override
    public boolean isExtra() {
      return node.isExtra();
    }

    @Override
    public boolean isMissing() {
      return node.isMissing();
    }

    @Override
    public boolean isNamed() {
      return node.isNamed();
    }

    @Override
    public boolean isNull() {
      return node.isNull();
    }

    @Override
    public TreeCursor walk() {
      return new OffsetTreeCursor(node, offset);
    }

    @Override
    public QueryCursor walk(@NotNull Query query) {
      throw new UnsupportedOperationException(UOE_MESSAGE_3);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      OffsetNode other = (OffsetNode) obj;
      return node.equals(other.node);
    }

    @Override
    public int hashCode() {
      return Objects.hash(node, offset);
    }

    @Override
    public String toString() {
      String original = node.toString();
      int lower = Node.class.getSimpleName().length() + 1;
      int upper = original.length() - 1;
      String data = original.substring(lower, upper);
      return String.format(
          "OffsetNode(%s, row: %d, column: %d)", data, offset.getRow(), offset.getColumn());
    }
  }

  @Override
  public TreeCursorNode getCurrentTreeCursorNode() {
    return new OffsetTreeCursorNode(cursor.getCurrentTreeCursorNode(), offset);
  }

  /*
   * An offset copy of the current tree cursor node.
   * Retains all the information from the original node,
   * except for the byte positions.
   */
  private static class OffsetTreeCursorNode extends TreeCursorNode {

    OffsetTreeCursorNode(TreeCursorNode cursorNode, Point offset) {
      this(
          cursorNode.getName(),
          cursorNode.getType(),
          cursorNode.getContent(),
          cursorNode.getStartPoint().add(offset),
          cursorNode.getEndPoint().add(offset),
          cursorNode.isNamed());
    }

    OffsetTreeCursorNode(
        String name,
        String type,
        String content,
        Point startPoint,
        Point endPoint,
        boolean isNamed) {
      super(
          name, type, content, Integer.MIN_VALUE, Integer.MAX_VALUE, startPoint, endPoint, isNamed);
    }

    @Override
    public int getEndByte() {
      throw new UnsupportedOperationException(UOE_MESSAGE_1);
    }

    @Override
    public int getStartByte() {
      throw new UnsupportedOperationException(UOE_MESSAGE_1);
    }
  }

  /*
   * A range that retains only Point information.
   * Byte positions are not provided by this implementation.
   */
  private static class PositionOnlyRange extends Range {

    PositionOnlyRange(Node node) {
      super(Integer.MIN_VALUE, Integer.MAX_VALUE, node.getStartPoint(), node.getEndPoint());
    }

    @Override
    public int getEndByte() {
      throw new UnsupportedOperationException(UOE_MESSAGE_4);
    }

    @Override
    public int getStartByte() {
      throw new UnsupportedOperationException(UOE_MESSAGE_4);
    }
  }
}
