package io.genie.treesitter.exception.parser;

import io.genie.treesitter.Language;
import lombok.experimental.StandardException;

@StandardException
public class IncompatibleLanguageException extends ParserException {

  private static final String TEMPLATE = "Could not assign language to parser: %s";

  public IncompatibleLanguageException(Language language) {
    super(String.format(TEMPLATE, language));
  }
}
