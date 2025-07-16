package io.genie.treesitter.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TreeSitterException extends RuntimeException {

  public TreeSitterException(String message, Throwable cause) {
    super(message, cause);
  }

  public TreeSitterException(String message) {
    super(message);
  }

  public TreeSitterException(Throwable cause) {
    super(cause);
  }
}
