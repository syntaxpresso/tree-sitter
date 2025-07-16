package io.genie.treesitter.printer;

import java.io.File;
import java.io.IOException;

public interface TreePrinter {

  String print();

  File export() throws IOException;
}
