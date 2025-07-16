package io.genie.treesitter.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IOExceptionThrowingConsumer<T> {

  void accept(T t) throws IOException;

  static <T> Consumer<T> toUnchecked(@NotNull IOExceptionThrowingConsumer<T> consumer) {
    Objects.requireNonNull(consumer, "Throwing consumer must not be null!");
    return t -> {
      try {
        consumer.accept(t);
      } catch (IOException ex) {
        throw new UncheckedIOException(ex);
      }
    };
  }
}
