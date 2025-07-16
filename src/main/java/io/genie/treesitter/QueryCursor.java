package io.genie.treesitter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QueryCursor extends External implements Iterable<QueryMatch> {

  Node node;
  Query query;

  @Getter @NonFinal boolean executed = false;

  QueryCursor(long pointer, @NotNull Node node, @NotNull Query query) {
    super(pointer);
    this.node = node;
    this.query = query;
  }

  @Override
  protected native void delete();

  public native void execute();

  public native void setRange(int startByte, int endByte);

  public native void setRange(@NotNull Point startPoint, @NotNull Point endPoint);

  public native QueryMatch nextMatch();

  @Override
  public @NotNull Iterator<QueryMatch> iterator() {
    execute();
    return new Iterator<>() {

      private QueryMatch current = nextMatch();

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public QueryMatch next() {
        if (!hasNext()) throw new NoSuchElementException();
        QueryMatch match = current;
        current = nextMatch();
        return match;
      }
    };
  }

  @Override
  @Generated
  public String toString() {
    return String.format("QueryCursor(node: %s, query: %s)", node, query);
  }
}
