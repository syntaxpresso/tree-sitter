package io.genie.treesitter;

import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Language {
  JAVA(java(), "java");

  private static native long java();

  public static void validate(@NotNull Language language) {
    Objects.requireNonNull(language, "Language must not be null!");
    long id = language.getId();
    if (id == Language.INVALID)
      throw new UnsatisfiedLinkError("Language binding has not been defined for: " + language);
  }

  public static @NotNull Collection<Language> associatedWith(@NotNull Path path) {
    Objects.requireNonNull(path, "Path argument must not be null!");
    if (Files.isDirectory(path))
      throw new IllegalArgumentException("Path argument must not be a directory!");
    String name = path.getFileName().toString();
    String extension = FilenameUtils.getExtension(name);
    return Optional.of(extension).map(EXTENSION_LOOKUP::get).orElseGet(Collections::emptyList);
  }

  private static native int version(long id);

  private static native int symbols(long id);

  private static native Symbol symbol(long languageId, int symbolId);

  private static native int fields(long id);

  private static native String field(long languageId, int fieldId);

  private static native int states(long id);

  long id;
  int version;
  int totalStates;
  List<Symbol> symbols;
  List<String> fields;
  List<String> extensions;

  private static final long INVALID = 0L;

  private static final Properties LANGUAGE_PROPERTIES = new Properties();

  static {
    ClassLoader loader = Language.class.getClassLoader();
    try (InputStream stream = loader.getResourceAsStream("language.properties")) {
      LANGUAGE_PROPERTIES.load(stream);
      LANGUAGE_PROPERTIES.entrySet().removeIf(entry -> entry.getValue().toString().isEmpty());
    } catch (Exception ex) {
      throw new ExceptionInInitializerError(ex);
    }
  }

  private static final Map<String, List<Language>> EXTENSION_LOOKUP =
      Stream.of(Language.values())
          .flatMap(
              language ->
                  language.getExtensions().stream()
                      .map(extension -> Map.entry(extension, language)))
          .collect(
              Collectors.groupingBy(
                  Map.Entry::getKey,
                  Collectors.mapping(Map.Entry::getValue, Collectors.toUnmodifiableList())));

  Language(long id, String... extensions) {
    this(id, version(id), states(id), symbols(id), fields(id), List.of(extensions));
  }

  Language(
      long id,
      int version,
      int totalStates,
      int totalSymbols,
      int totalFields,
      List<String> extensions) {
    this.id = id;
    this.version = version;
    this.totalStates = totalStates;
    this.symbols =
        IntStream.range(0, totalSymbols)
            .mapToObj(symbolId -> symbol(id, symbolId))
            .collect(Collectors.toUnmodifiableList());
    this.fields =
        IntStream.range(1, totalFields + 1)
            .mapToObj(fieldId -> field(id, fieldId))
            .collect(Collectors.toUnmodifiableList());
    this.extensions = extensions;
  }

  public native LookaheadIterator iterator(int state);

  public int nextState(@NotNull Node node) {
    Objects.requireNonNull(node, "Node must not be null!");
    Language language = node.getLanguage();
    if (!this.equals(language))
      throw new IllegalArgumentException(
          "Node language does not match the language of this instance!");
    int state = node.getParseState();
    Symbol symbol = node.getGrammarSymbol();
    return nextState(id, state, symbol.getId());
  }

  private static native int nextState(long id, int state, int symbol);

  @Generated
  @Deprecated(forRemoval = true, since = "1.12.0")
  public int getTotalSymbols() {
    return symbols.size();
  }

  @Generated
  @Deprecated(forRemoval = true, since = "1.12.0")
  public int getTotalFields() {
    return fields.size();
  }

  @Override
  public String toString() {
    switch (this) {
      case JAVA:
        return capitalize(name());
      default:
        return "???";
    }
  }

  private static String capitalize(String name) {
    return name.charAt(0) + name.substring(1).toLowerCase();
  }

  public Metadata getMetadata() {
    return new Metadata(getURL(), getSHA(), getTag());
  }

  private URL getURL() {
    String key = "url." + getSubmoduleName();
    String value = LANGUAGE_PROPERTIES.getProperty(key);
    if (value == null) return null;
    try {
      return new java.net.URI(value).toURL();
    } catch (java.net.URISyntaxException ex) {
      throw new IllegalArgumentException("Invalid URL syntax: " + value, ex);
    } catch (MalformedURLException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  private String getSHA() {
    String key = "sha." + getSubmoduleName();
    return LANGUAGE_PROPERTIES.getProperty(key);
  }

  private String getTag() {
    String key = "tag." + getSubmoduleName();
    return LANGUAGE_PROPERTIES.getProperty(key);
  }

  private String getSubmoduleName() {
    return name().toLowerCase().replace("_", "-");
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  public static final class Metadata {

    URL url;
    String sha;
    String tag;

    @Generated
    public URL getURL() {
      return url;
    }

    @Generated
    public String getSHA() {
      return sha;
    }

    @Generated
    public String getTag() {
      return tag;
    }
  }
}
