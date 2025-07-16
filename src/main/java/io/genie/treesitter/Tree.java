package io.genie.treesitter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Tree extends External implements Iterable<Node>, Cloneable {

  private static final Charset CHARSET = StandardCharsets.UTF_16LE;

  Language language;
  String source;

  Tree(long pointer, @NotNull Language language, @NotNull String source) {
    super(pointer);
    this.language = language;
    this.source = source;
  }

  @Override
  protected native void delete();

  public native void edit(@NotNull InputEdit edit);

  public native List<Range> getChangedRanges(@NotNull Tree other);

  public native Node getRootNode();

  @Override
  public @NotNull Iterator<Node> iterator() {
    return getRootNode().iterator();
  }

  @Override
  public native Tree clone();

  String getSource(int startByte, int endByte) {
    byte[] bytes = source.getBytes(CHARSET);
    byte[] copy = Arrays.copyOfRange(bytes, startByte * 2, endByte * 2);
    return new String(copy, CHARSET);
  }
}
