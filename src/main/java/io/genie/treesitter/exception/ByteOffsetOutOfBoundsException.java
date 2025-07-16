package io.genie.treesitter.exception;

public class ByteOffsetOutOfBoundsException extends NodeRangeBoundaryException {

  public ByteOffsetOutOfBoundsException(int offset) {
    super("Byte offset outside node range: " + offset);
  }
}
