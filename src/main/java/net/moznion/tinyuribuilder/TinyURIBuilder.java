package net.moznion.tinyuribuilder;

import lombok.Getter;
import lombok.NonNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TinyURIBuilder {
  @Getter
  private String scheme;
  @Getter
  private String host;
  @Getter
  private int port;
  @Getter
  private List<String> paths;
  @Getter
  private Map<String, String> queryParameters;
  @Getter
  private String fragment;

  /**
   * Create a new empty instance.
   * 
   * @throws URISyntaxException
   */
  public TinyURIBuilder() throws URISyntaxException {
    this(new URI(""));
  }

  /**
   * Create a new instance according to passed URI string.
   * 
   * @param uriString
   * @throws URISyntaxException
   */
  public TinyURIBuilder(@NonNull String uriString) throws URISyntaxException {
    this(new URI(uriString));
  }

  /**
   * Create a new instance according to passed URI instance.
   * 
   * @param uri
   */
  public TinyURIBuilder(@NonNull URI uri) {
    scheme = uri.getScheme();
    if (scheme == null) {
      scheme = "";
    }

    host = uri.getHost();
    if (host == null) {
      host = "";
    }

    port = uri.getPort();

    fragment = uri.getFragment();
    if (fragment == null) {
      fragment = "";
    }

    paths = new ArrayList<>();
    String pathString = uri.getPath();
    if (pathString != null) {
      paths.addAll(Arrays.asList(pathString.split("/")));
    }

    queryParameters = new TreeMap<>();
    String queryString = uri.getQuery();
    if (queryString != null) {
      queryParameters.putAll(Arrays.stream(queryString.split("&"))
          .map(term -> term.split("="))
          .filter(keyValue -> keyValue.length == 2)
          .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1])));
    }
  }

  /**
   * Set a schema.
   * 
   * @param schema
   * @return
   */
  public TinyURIBuilder setSchema(@NonNull String schema) {
    this.scheme = schema;
    return this;
  }

  /**
   * Set a host.
   * 
   * @param host
   * @return
   */
  public TinyURIBuilder setHost(@NonNull String host) {
    this.host = host;
    return this;
  }

  /**
   * Set port number.
   * 
   * If you pass a negative value to this argument, this builder deals as port isn't specified.
   * 
   * @param port
   * @return
   */
  public TinyURIBuilder setPort(int port) {
    this.port = port;
    return this;
  }

  /**
   * Set paths.
   * 
   * Replace current paths with argument.
   * 
   * @param paths
   * @return
   */
  public TinyURIBuilder setPaths(@NonNull List<String> paths) {
    this.paths.clear();
    this.paths.addAll(paths);
    return this;
  }

  /**
   * Set paths by string.
   * 
   * It splits paths string by "/" and replace paths with them.
   * 
   * @param paths
   * @return
   */
  public TinyURIBuilder setPaths(@NonNull String paths) {
    this.paths.clear();
    this.paths.addAll(Arrays.asList(paths.split("/")));
    return this;
  }

  /**
   * Append paths to current paths.
   * 
   * @param paths
   * @return
   */
  public TinyURIBuilder appendPaths(@NonNull List<String> paths) {
    this.paths.addAll(paths);
    return this;
  }

  /**
   * Append paths to current paths.
   * 
   * It splits paths string by "/" and append them to current paths.
   * 
   * @param paths
   * @return
   */
  public TinyURIBuilder appendPaths(@NonNull String paths) {
    this.paths.addAll(Arrays.asList(paths.split("/")));
    return this;
  }

  /**
   * Append a path to current paths.
   * 
   * @param path
   * @return
   */
  public TinyURIBuilder appendPath(@NonNull String path) {
    this.paths.add(path);
    return this;
  }

  /**
   * Set query parameters.
   * 
   * Replace current query parameters with argument.
   * 
   * @param queryParameters
   * @return
   */
  public TinyURIBuilder setQueryParameters(@NonNull Map<String, String> queryParameters) {
    this.queryParameters.clear();
    this.queryParameters.putAll(queryParameters);
    return this;
  }

  /**
   * Add query parameters.
   * 
   * @param queryParameters
   * @return
   */
  public TinyURIBuilder addQueryParameters(@NonNull Map<String, String> queryParameters) {
    this.queryParameters.putAll(queryParameters);
    return this;
  }

  /**
   * Add a query parameters.
   * 
   * @param key
   * @param value
   * @return
   */
  public TinyURIBuilder addQueryParameter(@NonNull String key, @NonNull String value) {
    this.queryParameters.put(key, value);
    return this;
  }

  /**
   * Set a fragment.
   * 
   * @param fragment
   * @return
   */
  public TinyURIBuilder setFragment(@NonNull String fragment) {
    this.fragment = fragment;
    return this;
  }

  private static final Pattern CONSECUTIVE_SLASHES_RE = Pattern.compile("/+");

  /**
   * Build a new URI instance by according to builder's information.
   * 
   * @return
   * @throws URISyntaxException
   */
  public URI build() throws URISyntaxException {
    StringBuilder uriStringBuilder = new StringBuilder();

    if (!scheme.isEmpty()) {
      uriStringBuilder.append(scheme).append("://");
    }

    boolean shouldAppendTrailingSlash = false;
    if (!host.isEmpty()) {
      uriStringBuilder.append(host);

      if (host.substring(host.length() - 1).equals("/")) { // is last character slash?
        shouldAppendTrailingSlash = true;
      }
    }

    if (port >= 0) {
      uriStringBuilder.append(":").append(port);
    }

    if (!paths.isEmpty()) {
      for (String path : paths) {
        if (!path.isEmpty()) {
          uriStringBuilder.append("/").append(path);
        }
      }

      if (shouldAppendTrailingSlash) {
        uriStringBuilder.append("/");
      }
    }

    if (!queryParameters.isEmpty()) {
      uriStringBuilder.append("?");

      List<String> terms = new ArrayList<>();
      for (Entry<String, String> queryParameter : queryParameters.entrySet()) {
        terms.add(queryParameter.getKey() + "=" + queryParameter.getValue());
      }

      uriStringBuilder.append(String.join("&", terms));
    }

    if (!fragment.isEmpty()) {
      uriStringBuilder.append("#").append(fragment);
    }

    String uriString = uriStringBuilder.toString();

    uriString = CONSECUTIVE_SLASHES_RE.matcher(uriString).replaceAll("/"); // Squash consecutive
                                                                           // slashes

    return new URI(uriStringBuilder.toString());
  }
}
