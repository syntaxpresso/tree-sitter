package io.genie.treesitter;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Node implements Iterable<Node> {

  int context0;
  int context1;
  int context2;
  int context3;

  long id;

  Tree tree;

  static class Null extends Node {}

  public Node getChild(int child) {
    return getChild(child, false);
  }

  private native Node getChild(int child, boolean named);

  public native Node getChildByFieldName(@NotNull String name);

  public int getChildCount() {
    return getChildCount(false);
  }

  private native int getChildCount(boolean named);

  public List<Node> getChildren() {
    Node[] children = Node.getChildren(this, false);
    return List.of(children);
  }

  private static native Node[] getChildren(Node node, boolean named);

  public String getContent() {
    return !isNull() ? tree.getSource(getStartByte(), getEndByte()) : null;
  }

  public Node getDescendant(int startByte, int endByte) {
    return getDescendant(startByte, endByte, false);
  }

  private native Node getDescendant(int startByte, int endByte, boolean named);

  public Node getDescendant(@NotNull Point startPoint, @NotNull Point endPoint) {
    return getDescendant(startPoint, endPoint, false);
  }

  private native Node getDescendant(Point startPoint, Point endPoint, boolean named);

  public native int getDescendantCount();

  public native int getEndByte();

  public native Point getEndPoint();

  public native String getFieldNameForChild(int child);

  public Symbol getGrammarSymbol() {
    return getSymbol(true);
  }

  public String getGrammarType() {
    return getType(true);
  }

  public Node getFirstChildForByte(int offset) {
    return getFirstChildForByte(offset, false);
  }

  private native Node getFirstChildForByte(int offset, boolean named);

  public Node getFirstNamedChildForByte(int offset) {
    return getFirstChildForByte(offset, true);
  }

  public Language getLanguage() {
    return !isNull() ? tree.getLanguage() : null;
  }

  public Node getNamedChild(int child) {
    return getChild(child, true);
  }

  public int getNamedChildCount() {
    return getChildCount(true);
  }

  public List<Node> getNamedChildren() {
    Node[] children = Node.getChildren(this, true);
    return List.of(children);
  }

  public Node getNamedDescendant(int startByte, int endByte) {
    return getDescendant(startByte, endByte, true);
  }

  public Node getNamedDescendant(@NotNull Point startPoint, @NotNull Point endPoint) {
    return getDescendant(startPoint, endPoint, true);
  }

  public Node getNextNamedSibling() {
    return getNextSibling(true);
  }

  public native int getNextParseState();

  public Node getNextSibling() {
    return getNextSibling(false);
  }

  private native Node getNextSibling(boolean named);

  public Node getPrevNamedSibling() {
    return getPrevSibling(true);
  }

  public Node getPrevSibling() {
    return getPrevSibling(false);
  }

  private native Node getPrevSibling(boolean named);

  public native Node getParent();

  public native int getParseState();

  public Range getRange() {
    return new Range(this);
  }

  public native int getStartByte();

  public native Point getStartPoint();

  public Symbol getSymbol() {
    return getSymbol(false);
  }

  private native Symbol getSymbol(boolean grammar);

  public String getType() {
    return getType(false);
  }

  private native String getType(boolean grammar);

  public native boolean hasChanges();

  public native boolean hasError();

  public native boolean isError();

  public native boolean isExtra();

  public native boolean isMissing();

  public native boolean isNamed();

  public native boolean isNull();

  public native TreeCursor walk();

  public native QueryCursor walk(@NotNull Query query);

  @Override
  @Generated
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Node other = (Node) obj;
    return equals(this, other);
  }

  private static native boolean equals(@NotNull Node node, @NotNull Node other);

  @Override
  @Generated
  public int hashCode() {
    return Long.hashCode(id);
  }

  @Override
  @Generated
  public String toString() {
    return String.format("Node(id: %d, tree: %s)", id, tree);
  }

  @Override
  public @NotNull Iterator<Node> iterator() {
    return new Iterator<>() {

      private final Deque<Node> stack = new ArrayDeque<>(Collections.singletonList(Node.this));

      @Override
      public boolean hasNext() {
        return !stack.isEmpty();
      }

      @Override
      public Node next() {
        if (!hasNext()) throw new NoSuchElementException();
        Node node = stack.pop();
        stack.addAll(node.getChildren());
        return node;
      }
    };
  }
}
