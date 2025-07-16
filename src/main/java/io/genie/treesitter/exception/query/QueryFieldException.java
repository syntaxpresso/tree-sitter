package io.genie.treesitter.exception.query;

import lombok.experimental.StandardException;

@StandardException
public class QueryFieldException extends QueryException {

  public QueryFieldException(int offset) {
    super("Bad field name at offset " + offset);
  }
}
