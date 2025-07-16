package io.genie.treesitter;

import io.genie.treesitter.exception.query.QueryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Query extends External {

  Language language;
  List<Pattern> patterns;
  List<Capture> captures;
  List<String> strings;

  Query(
      long pointer,
      @NotNull Language language,
      @NotNull Pattern[] patterns,
      @NotNull Capture[] captures,
      @NotNull String[] strings) {
    super(pointer);
    this.language = language;
    this.patterns = List.of(patterns);
    this.captures = List.of(captures);
    this.strings = List.of(strings);
  }

  public static Query getFor(@NotNull Language language, @NotNull String... patterns) {
    return builder().language(language).patterns(patterns).build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public Builder toBuilder() {
    Language language = getLanguage();
    List<String> patterns =
        getPatterns().stream().map(Pattern::toString).collect(Collectors.toList());
    return builder().language(language).patterns(patterns);
  }

  @FieldDefaults(level = AccessLevel.PRIVATE)
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Builder {

    Language language = null;

    List<String> patterns = new ArrayList<>();

    public Builder language(@NotNull Language language) {
      Language.validate(language);
      this.language = language;
      return this;
    }

    public Builder patterns(@NotNull List<@NotNull String> patterns) {
      Objects.requireNonNull(patterns, "Patterns must not be null!");
      this.patterns = List.copyOf(patterns).stream().map(String::trim).collect(Collectors.toList());
      return this;
    }

    public Builder patterns(@NotNull String... patterns) {
      Objects.requireNonNull(patterns, "Patterns must not be null!");
      for (String pattern : patterns) pattern(pattern);
      return this;
    }

    public Builder pattern(@NotNull String pattern) {
      Objects.requireNonNull(pattern, "Pattern must not be null!");
      patterns.add(pattern.trim());
      return this;
    }

    public Builder pattern() {
      patterns.clear();
      return this;
    }

    public Query build() {
      Objects.requireNonNull(language, "Language must not be null!");
      String joined = String.join(" ", patterns);
      return build(language, normalize(joined));
    }

    private static String normalize(String pattern) {
      return StringUtils.normalizeSpace(pattern)
          .replace(" )", ")")
          .replace("( ", "(")
          .replace(" ]", "]")
          .replace("[ ", "[");
    }

    private static native Query build(Language language, String pattern) throws QueryException;
  }

  @Override
  protected native void delete();

  public boolean hasCaptures() {
    return !captures.isEmpty();
  }

  @Override
  @Generated
  public String toString() {
    String pattern = getPattern();
    String capture =
        captures.stream().map(Capture::toString).collect(Collectors.joining(", ", "[", "]"));
    return String.format(
        "Query(language: %s, pattern: '%s', captures: %s)", language, pattern, capture);
  }

  @Generated
  public String getPattern() {
    return patterns.stream().map(Pattern::toString).collect(Collectors.joining(" "));
  }

  Quantifier getQuantifier(@NotNull Pattern pattern, @NotNull Capture capture) {
    Objects.requireNonNull(pattern, "Pattern must not be null!");
    Objects.requireNonNull(capture, "Capture must not be null!");
    if (!patterns.contains(pattern))
      throw new IllegalArgumentException("Pattern not present in query!");
    if (!captures.contains(capture))
      throw new IllegalArgumentException("Capture not present in query!");
    Quantifier[] quantifiers = Quantifier.values();
    int ordinal = getQuantifier(pattern.getIndex(), capture.getIndex());
    return quantifiers[ordinal];
  }

  private native int getQuantifier(int patternIndex, int captureIndex);
}
