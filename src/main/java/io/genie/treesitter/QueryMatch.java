package io.genie.treesitter;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.UnmodifiableMultiValuedMap;
import org.jetbrains.annotations.NotNull;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QueryMatch {

  int id;
  Pattern pattern;
  MultiValuedMap<Capture, Node> captures;

  QueryMatch(int id, @NotNull Pattern pattern, @NotNull Map.Entry<Capture, Node>[] captures) {
    this.id = id;
    this.pattern = pattern;
    this.captures =
        UnmodifiableMultiValuedMap.unmodifiableMultiValuedMap(
            Stream.of(captures)
                .collect(
                    ArrayListValuedHashMap::new,
                    (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                    MultiValuedMap::putAll));
  }

  public Map<Capture, Collection<Node>> getCaptures() {
    return captures.asMap();
  }

  public Collection<Node> getNodes() {
    return captures.values();
  }

  public Collection<Node> getNodes(Capture capture) {
    return captures.get(capture);
  }

  public Collection<Node> getNodes(String name) {
    return captures.entries().stream()
        .filter(
            entry -> {
              Capture capture = entry.getKey();
              return name.equals(capture.getName());
            })
        .map(Map.Entry::getValue)
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  @Generated
  public String toString() {
    String joined =
        captures.entries().stream()
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining(", ", "{", "}"));
    return String.format("QueryMatch(id: %d, pattern: '%s', captures: [%s])", id, pattern, joined);
  }
}
