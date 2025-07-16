package io.genie.treesitter.error;

import lombok.experimental.StandardException;

@StandardException
public class ABIVersionError extends LinkageError {

  private static final String TEMPLATE = "Incompatible language version: %d";

  public ABIVersionError(int version) {
    super(String.format(TEMPLATE, version));
  }
}
