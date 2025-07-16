package io.genie.treesitter;

import java.util.Objects;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TreeCursor extends External implements Cloneable {

  int context0;
  int context1;

  long id;

  Tree tree;

  TreeCursor(long pointer, int context0, int context1, long id, @NotNull Tree tree) {
    super(pointer);
    this.context0 = context0;
    this.context1 = context1;
    this.id = id;
    this.tree = tree;
  }

  @Override
  protected native void delete();

  public native int getCurrentDepth();

  public native Node getCurrentNode();

  public native String getCurrentFieldName();

  public native TreeCursorNode getCurrentTreeCursorNode();

  public native boolean gotoFirstChild();

  public native boolean gotoFirstChild(int offset);

  public native boolean gotoFirstChild(@NotNull Point point);

  public native boolean gotoLastChild();

  public native boolean gotoNextSibling();

  public native boolean gotoPrevSibling();

  public native boolean gotoParent();

  public native boolean gotoNode(@NotNull Node node);

  public void preorderTraversal(@NotNull Consumer<Node> callback) {
    Objects.requireNonNull(callback, "Callback must not be null!");
    for (; ; ) {
      callback.accept(getCurrentNode());
      if (gotoFirstChild() || gotoNextSibling()) continue;
      do {
        if (!gotoParent()) return;
      } while (!gotoNextSibling());
    }
  }

  public native boolean reset(@NotNull TreeCursor other);

  @Override
  public native TreeCursor clone();

  static class Stub extends TreeCursor {

    @Override
    public int getCurrentDepth() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Node getCurrentNode() {
      throw new UnsupportedOperationException();
    }

    @Override
    public String getCurrentFieldName() {
      throw new UnsupportedOperationException();
    }

    @Override
    public TreeCursorNode getCurrentTreeCursorNode() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean gotoFirstChild() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean gotoFirstChild(int offset) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean gotoFirstChild(@NotNull Point point) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean gotoLastChild() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean gotoNextSibling() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean gotoPrevSibling() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean gotoParent() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean gotoNode(@NotNull Node node) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void preorderTraversal(@NotNull Consumer<Node> callback) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean reset(@NotNull TreeCursor other) {
      throw new UnsupportedOperationException();
    }

    @Override
    public TreeCursor clone() {
      throw new UnsupportedOperationException();
    }
  }
}
