package io.genie.treesitter.exception.query;

import lombok.experimental.StandardException;

@StandardException
public class QuerySyntaxException extends QueryException {

  public QuerySyntaxException(int offset) {
    super("Bad syntax at offset " + offset);
  }
}
