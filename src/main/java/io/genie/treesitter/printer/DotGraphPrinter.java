package io.genie.treesitter.printer;

import io.genie.treesitter.Tree;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DotGraphPrinter implements TreePrinter {

  Tree tree;

  public DotGraphPrinter(@NotNull Tree tree) {
    this.tree = Objects.requireNonNull(tree, "Tree must not be null!");
  }

  @Override
  public String print() {
    try {
      File file = export();
      Path path = file.toPath();
      String contents = Files.readString(path);
      Files.delete(path);
      return contents;
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  @Override
  public File export() throws IOException {
    File file = Files.createTempFile("ts-export-", ".dot").toFile();
    write(file);
    return file;
  }

  private native void write(File file) throws IOException;
}
