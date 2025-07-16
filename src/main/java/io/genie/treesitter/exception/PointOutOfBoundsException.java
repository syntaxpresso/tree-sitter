package io.genie.treesitter.exception;

import io.genie.treesitter.Point;

public class PointOutOfBoundsException extends NodeRangeBoundaryException {

  public PointOutOfBoundsException(Point point) {
    super("Point outside node range: " + point);
  }
}
