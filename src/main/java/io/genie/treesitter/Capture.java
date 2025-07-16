package io.genie.treesitter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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
public class Capture {

  Query query;

  int index;

  String name;

  @NonFinal boolean enabled = true;

  Capture(int index, @NotNull String name) {
    this(null, index, name);
  }

  public native void disable();

  public Quantifier getQuantifier(@NotNull Pattern pattern) {
    return query.getQuantifier(pattern, this);
  }

  public List<Quantifier> getQuantifiers() {
    return query.getPatterns().stream()
        .map(this::getQuantifier)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  @Generated
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Capture capture = (Capture) o;
    return Objects.equals(query, capture.query) && index == capture.index;
  }

  @Override
  @Generated
  public int hashCode() {
    return Objects.hash(query, index);
  }

  @Override
  @Generated
  public String toString() {
    return "@" + name;
  }
}
