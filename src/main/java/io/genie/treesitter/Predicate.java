package io.genie.treesitter;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Predicate {

  Pattern pattern;
  List<Step> steps;

  Predicate(Pattern pattern, Step[] steps) {
    this(pattern, List.of(steps));
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < steps.size() - 1; i++) {
      Step step = steps.get(i);
      String value = step.getValue();
      if (i == 0) {
        builder.append(value);
        continue;
      }
      builder.append(" ");
      switch (step.getType()) {
        case CAPTURE:
          builder.append("@").append(value);
          break;
        case STRING:
          builder.append('"').append(value).append('"');
          break;
        default:
      }
    }

    return "(#" + builder + ")";
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static class Step {

    Type type;
    String value;

    public Step(int type, String value) {
      this(Type.get(type), value);
    }

    public enum Type {
      DONE,

      CAPTURE,

      STRING;

      private static final Type[] VALUES = values();

      private static Type get(int ordinal) {
        return VALUES[ordinal];
      }
    }
  }
}
