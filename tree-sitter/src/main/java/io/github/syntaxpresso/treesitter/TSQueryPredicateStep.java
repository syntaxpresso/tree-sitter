package io.github.syntaxpresso.treesitter;

public class TSQueryPredicateStep {
  private TSQueryPredicateStepType type;
  private int valueId;

  public TSQueryPredicateStepType getType() {
    return type;
  }

  public int getValueId() {
    return valueId;
  }
}
