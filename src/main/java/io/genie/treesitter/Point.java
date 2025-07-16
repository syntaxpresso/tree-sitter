package io.genie.treesitter;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Point implements Comparable<Point> {

  @Getter(lazy = true)
  @Accessors(fluent = true, makeFinal = true)
  private static final Point ORIGIN = new Point(0, 0);

  int row;
  int column;

  @Override
  @Generated
  public String toString() {
    return row + ":" + column;
  }

  public boolean isOrigin() {
    return equals(ORIGIN());
  }

  @Override
  public int compareTo(@NotNull Point other) {
    Objects.requireNonNull(other, "Other point must not be null!");
    int compare = Integer.compare(row, other.row);
    return compare != 0 ? compare : Integer.compare(column, other.column);
  }

  public Point add(@NotNull Point other) {
    Objects.requireNonNull(other, "Other point must not be null!");
    if (isOrigin()) return other;
    if (other.isOrigin()) return this;
    return add(other.row, other.column);
  }

  public Point subtract(@NotNull Point other) {
    Objects.requireNonNull(other, "Other point must not be null!");
    if (other.isOrigin()) return this;
    if (equals(other)) return ORIGIN();
    return add(-other.row, -other.column);
  }

  private Point add(int row, int column) {
    return new Point(this.row + row, this.column + column);
  }

  public Point multiply(int value) {
    switch (value) {
      case 0:
        return ORIGIN();
      case 1:
        return this;
      default:
        return new Point(row * value, column * value);
    }
  }
}
