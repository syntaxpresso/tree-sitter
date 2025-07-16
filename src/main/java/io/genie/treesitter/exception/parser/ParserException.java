package io.genie.treesitter.exception.parser;

import io.genie.treesitter.exception.TreeSitterException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ParserException extends TreeSitterException {
  protected ParserException(String message, Throwable cause) {
    super(message, cause);
  }

  protected ParserException(String message) {
    super(message);
  }
}
