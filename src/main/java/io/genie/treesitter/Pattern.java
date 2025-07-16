package io.genie.treesitter;

import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Pattern {

  Query query;

  int index;

  int startOffset;

  boolean rooted;
  boolean nonLocal;

  String value;

  List<Predicate> predicates;

  @NonFinal boolean enabled = true;

  Pattern(
      int index,
      int startOffset,
      boolean rooted,
      boolean nonLocal,
      @NotNull String value,
      @NotNull Predicate[] predicates) {
    this(null, index, startOffset, rooted, nonLocal, value.stripTrailing(), List.of(predicates));
  }

  public native void disable();

  @Generated
  public int getEndOffset() {
    return startOffset + value.length();
  }

  @Override
  @Generated
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pattern pattern = (Pattern) o;
    return Objects.equals(query, pattern.query) && index == pattern.index;
  }

  @Override
  @Generated
  public int hashCode() {
    return Objects.hash(query, index);
  }

  @Override
  @Generated
  public String toString() {
    return value;
  }
}
