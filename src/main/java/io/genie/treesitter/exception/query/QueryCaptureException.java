package io.genie.treesitter.exception.query;

import lombok.experimental.StandardException;

@StandardException
public class QueryCaptureException extends QueryException {

  public QueryCaptureException(int offset) {
    super("Bad capture at offset " + offset);
  }
}
