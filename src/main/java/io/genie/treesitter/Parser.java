package io.genie.treesitter;

import io.genie.treesitter.exception.parser.IncompatibleLanguageException;
import io.genie.treesitter.exception.parser.ParsingException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Parser extends External {

  Language language;

  private static final Charset CHARSET = StandardCharsets.UTF_16LE;

  private static final String NULL_RANGE = "Range must not be null!";
  private static final String NULL_RANGES = "Ranges must not be null!";
  private static final String NULL_LANGUAGE = "Language must not be null!";
  private static final String NULL_DURATION = "Duration must not be null!";
  private static final String NULL_TIME_UNIT = "Time unit must not be null!";
  private static final String NEGATIVE_TIMEOUT = "Timeout must not be negative!";
  private static final String NEGATIVE_DURATION = "Duration must not be negative!";
  private static final String OVERLAPPING_RANGES = "Ranges must not overlap!";

  Parser(long pointer, @NotNull Language language) {
    super(pointer);
    this.language = language;
  }

  public static Parser getFor(@NotNull Language language) {
    return builder().language(language).build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return builder().language(getLanguage()).timeout(getTimeout()).ranges(getIncludedRanges());
  }

  @FieldDefaults(level = AccessLevel.PRIVATE)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Builder {

    Language language = null;

    long timeout = 0L;

    List<Range> ranges = new ArrayList<>();

    public Builder language(@NotNull Language language) {
      Language.validate(language);
      this.language = language;
      return this;
    }

    public Builder timeout(@NotNull Duration duration) {
      Objects.requireNonNull(duration, NULL_DURATION);
      if (duration.isNegative()) throw new IllegalArgumentException(NEGATIVE_DURATION);
      long micros = duration.toMillis() * TimeUnit.MILLISECONDS.toMicros(1);
      return timeout(micros);
    }

    public Builder timeout(long timeout, @NotNull TimeUnit timeUnit) {
      if (timeout < 0) throw new IllegalArgumentException(NEGATIVE_TIMEOUT);
      Objects.requireNonNull(timeUnit, NULL_TIME_UNIT);
      long micros = timeUnit.toMicros(timeout);
      return timeout(micros);
    }

    public Builder timeout(long timeout) {
      if (timeout < 0) throw new IllegalArgumentException(NEGATIVE_TIMEOUT);
      this.timeout = timeout;
      return this;
    }

    public Builder ranges(@NotNull List<@NotNull Range> ranges) {
      Objects.requireNonNull(ranges, NULL_RANGES);
      this.ranges = new ArrayList<>(List.copyOf(ranges));
      return this;
    }

    public Builder ranges(@NotNull Range... ranges) {
      Objects.requireNonNull(ranges, NULL_RANGES);
      for (Range range : ranges) range(range);
      return this;
    }

    public Builder range(@NotNull Range range) {
      Objects.requireNonNull(range, NULL_RANGE);
      ranges.add(range);
      return this;
    }

    public Builder range() {
      ranges.clear();
      return this;
    }

    public Parser build() {
      Objects.requireNonNull(language, NULL_LANGUAGE);
      Range[] array = validated(ranges.toArray(Range[]::new));
      return build(language, timeout, array, array.length);
    }

    private static native Parser build(Language language, long timeout, Range[] ranges, int length);
  }

  @Override
  protected native void delete();

  public void setLanguage(@NotNull Language language) {
    Language.validate(language);
    setLanguage(this, language);
  }

  private static native void setLanguage(Parser parser, Language language)
      throws IncompatibleLanguageException;

  public native Logger getLogger();

  public native void setLogger(Logger logger);

  public native List<Range> getIncludedRanges();

  public void setIncludedRanges(@NotNull List<@NotNull Range> ranges) {
    Objects.requireNonNull(ranges, NULL_RANGES);
    setIncludedRanges(ranges.toArray(Range[]::new));
  }

  public void setIncludedRanges(@NotNull Range... ranges) {
    Objects.requireNonNull(ranges, NULL_RANGES);
    setIncludedRanges(validated(ranges), ranges.length);
  }

  private static Range[] validated(Range[] ranges) {
    switch (ranges.length) {
      case 0:
        break;
      case 1:
        Objects.requireNonNull(ranges[0], NULL_RANGE);
        break;
      default:
        Range[] left = Arrays.copyOfRange(ranges, 0, ranges.length - 1);
        Range[] right = Arrays.copyOfRange(ranges, 1, ranges.length);
        for (int i = 0; i < ranges.length - 1; i++) {
          Objects.requireNonNull(left[i], NULL_RANGE);
          Objects.requireNonNull(right[i], NULL_RANGE);
          if (left[i].getEndByte() > right[i].getStartByte())
            throw new IllegalArgumentException(OVERLAPPING_RANGES);
        }
    }
    return ranges;
  }

  private native void setIncludedRanges(Range[] ranges, int length);

  public native long getTimeout();

  public void setTimeout(@NotNull Duration duration) {
    Objects.requireNonNull(duration, NULL_DURATION);
    long micros = duration.toMillis() * TimeUnit.MILLISECONDS.toMicros(1);
    setTimeout(micros);
  }

  public void setTimeout(long timeout, @NotNull TimeUnit timeUnit) {
    if (timeout < 0) throw new IllegalArgumentException(NEGATIVE_TIMEOUT);
    Objects.requireNonNull(timeUnit, NULL_TIME_UNIT);
    long micros = timeUnit.toMicros(timeout);
    setTimeout(micros);
  }

  public native void setTimeout(long timeout);

  public Tree parse(@NotNull String source) throws ParsingException {
    byte[] bytes = source.getBytes(CHARSET);
    return parse(source, bytes, bytes.length, null);
  }

  public Tree parse(@NotNull String source, @NotNull Tree oldTree) throws ParsingException {
    byte[] bytes = source.getBytes(CHARSET);
    return parse(source, bytes, bytes.length, oldTree);
  }

  public Tree parse(@NotNull Path path) throws ParsingException {
    try {
      String source = Files.readString(path);
      return parse(source);
    } catch (IOException ex) {
      throw new ParsingException(ex);
    }
  }

  public Tree parse(@NotNull Path path, @NotNull Tree oldTree) throws ParsingException {
    try {
      String source = Files.readString(path);
      return parse(source, oldTree);
    } catch (IOException ex) {
      throw new ParsingException(ex);
    }
  }

  private native Tree parse(String source, byte[] bytes, int length, Tree oldTree);
}
