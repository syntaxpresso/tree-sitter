package io.genie.treesitter.exception.query;

import lombok.experimental.StandardException;

@StandardException
public class QueryStructureException extends QueryException {

  public QueryStructureException(int offset) {
    super("Bad pattern structure at offset " + offset);
  }
}
