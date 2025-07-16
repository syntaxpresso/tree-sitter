package io.genie.treesitter;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Range {

  int startByte;
  int endByte;
  Point startPoint;
  Point endPoint;

  public static Builder builder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return builder()
        .startByte(startByte)
        .endByte(endByte)
        .startPoint(startPoint)
        .endPoint(endPoint);
  }

  @FieldDefaults(level = AccessLevel.PRIVATE)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Builder {

    int startByte = 0;
    int endByte = Integer.MAX_VALUE;
    Point startPoint = Point.ORIGIN();
    Point endPoint = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

    public Builder startByte(int value) {
      if (value < 0) throw new IllegalArgumentException("Start byte cannot be negative");
      startByte = value;
      return this;
    }

    public Builder endByte(int value) {
      if (value < 0) throw new IllegalArgumentException("End byte cannot be negative");
      endByte = value;
      return this;
    }

    public Builder startPoint(@NotNull Point point) {
      Objects.requireNonNull(point, "Start point cannot be null");
      if (point.getRow() < 0 || point.getColumn() < 0)
        throw new IllegalArgumentException("Start point cannot have negative coordinates");
      startPoint = point;
      return this;
    }

    public Builder endPoint(@NotNull Point point) {
      Objects.requireNonNull(point, "End point cannot be null");
      if (point.getRow() < 0 || point.getColumn() < 0)
        throw new IllegalArgumentException("End point cannot have negative coordinates");
      endPoint = point;
      return this;
    }

    public Range build() {
      if (Integer.compareUnsigned(startByte, endByte) > 0)
        throw new IllegalArgumentException("Start byte cannot be greater than end byte");
      if (startPoint.compareTo(endPoint) > 0)
        throw new IllegalArgumentException("Start point cannot be greater than end point");
      return new Range(startByte, endByte, startPoint, endPoint);
    }
  }

  Range(@NotNull Node node) {
    this(node.getStartByte(), node.getEndByte(), node.getStartPoint(), node.getEndPoint());
  }

  @Override
  @Generated
  public String toString() {
    return String.format("[%s] - [%s]", startPoint, endPoint);
  }
}
