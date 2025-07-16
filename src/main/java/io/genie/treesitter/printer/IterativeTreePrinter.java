package io.genie.treesitter.printer;

import io.genie.treesitter.TreeCursor;
import io.genie.treesitter.function.IOExceptionThrowingConsumer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
abstract class IterativeTreePrinter implements TreePrinter {

  TreeCursor cursor;

  protected IterativeTreePrinter(@NotNull TreeCursor cursor) {
    this.cursor = Objects.requireNonNull(cursor, "Cursor must not be null!");
  }

  public final String print() {
    StringBuilder stringBuilder = new StringBuilder(getPreamble());
    write(stringBuilder::append);
    return stringBuilder.toString();
  }

  public final File export() throws IOException {
    File file = Files.createTempFile("ts-export-", getFileExtension()).toFile();
    try (Writer writer = new BufferedWriter(new FileWriter(file))) {
      writer.append(getPreamble());
      Consumer<String> appender = IOExceptionThrowingConsumer.toUnchecked(writer::append);
      write(appender);
      return file;
    } catch (UncheckedIOException ex) {
      throw ex.getCause();
    }
  }

  protected String getPreamble() {
    return "";
  }

  protected String getFileExtension() {
    return "";
  }

  protected abstract void write(Consumer<String> appender);
}
