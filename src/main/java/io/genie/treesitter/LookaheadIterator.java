package io.genie.treesitter;

import java.util.Iterator;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class LookaheadIterator extends External implements Iterator<Symbol> {

  boolean hasNext;

  @Getter Language language;

  LookaheadIterator(long pointer, boolean hasNext, Language language) {
    super(pointer);
    this.hasNext = hasNext;
    this.language = language;
  }

  @Override
  protected native void delete();

  @Override
  public boolean hasNext() {
    return hasNext;
  }

  @Override
  public native Symbol next();
}
