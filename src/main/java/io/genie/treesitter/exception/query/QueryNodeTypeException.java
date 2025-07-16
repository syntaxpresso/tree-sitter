package io.genie.treesitter.exception.query;

import lombok.experimental.StandardException;

@StandardException
public class QueryNodeTypeException extends QueryException {

  public QueryNodeTypeException(int offset) {
    super("Bad node name at offset " + offset);
  }
}
