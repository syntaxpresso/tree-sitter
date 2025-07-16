package io.genie.treesitter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TreeCursorNode {

  String name;
  String type;
  String content;
  int startByte;
  int endByte;
  Point startPoint;
  Point endPoint;
  boolean isNamed;

  TreeCursorNode(String name, Node node) {
    this(
        name,
        node.getType(),
        node.getContent(),
        node.getStartByte(),
        node.getEndByte(),
        node.getStartPoint(),
        node.getEndPoint(),
        node.isNamed());
  }

  @Override
  @Generated
  public String toString() {
    String field = name != null ? name + ": " : "";
    return String.format("%s%s [%s] - [%s]", field, type, startPoint, endPoint);
  }
}
