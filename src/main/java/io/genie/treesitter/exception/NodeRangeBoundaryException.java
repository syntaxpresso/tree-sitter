package io.genie.treesitter.exception;

import lombok.AccessLevel;
import lombok.experimental.StandardException;

@StandardException(access = AccessLevel.PROTECTED)
abstract class NodeRangeBoundaryException extends TreeSitterException {}
