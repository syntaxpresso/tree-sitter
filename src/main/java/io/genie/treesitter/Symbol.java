package io.genie.treesitter;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Symbol {

  int id;
  Type type;
  String name;

  Symbol(int id, int ordinal, String name) {
    this(id, Type.get(ordinal), name);
  }

  @Override
  @Generated
  public String toString() {
    return String.format("Symbol(id: %d, type: %s, name: '%s')", id, type, name);
  }

  @Override
  @Generated
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Symbol symbol = (Symbol) obj;
    return id == symbol.id && type == symbol.type && Objects.equals(name, symbol.name);
  }

  @Override
  @Generated
  public int hashCode() {
    return Objects.hash(id, type, name);
  }

  public enum Type {
    REGULAR,
    ANONYMOUS,
    AUXILIARY;

    private static final Type[] VALUES = values();

    private static Type get(int ordinal) {
      return VALUES[ordinal];
    }
  }
}
