package net.moznion.uribuildertiny;

import lombok.Getter;
import lombok.NonNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Minimal URI builder.
 * 
 * @author moznion
 */
public class URIBuilderTiny {
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

  private final URLEncoder urlEncoder;

  /**
   * Create a new empty instance.
   * 
   * @throws URISyntaxException
   */
  public URIBuilderTiny() throws URISyntaxException {
    this(new URI(""));
  }

  /**
   * Create a new instance according to passed URI string.
   * 
   * This method doesn't apply percent-encoding to URI string which is passed via argument.
   * 
   * @param uriString
   * @throws URISyntaxException
   */
  public URIBuilderTiny(@NonNull String uriString) throws URISyntaxException {
    this(new URI(uriString));
  }

  /**
   * Create a new instance according to passed URI instance.
   * 
   * This method doesn't apply percent-encoding to URI which is passed via argument.
   * 
   * @param uri
   */
  public URIBuilderTiny(@NonNull URI uri) {
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
    if (pathString != null && !pathString.isEmpty()) {
      paths.addAll(Arrays.asList(pathString.split("/")));
    }

    queryParameters = new TreeMap<>();
    String queryString = uri.getQuery();
    if (queryString != null && !queryString.isEmpty()) {
      queryParameters.putAll(Arrays.stream(queryString.split("&"))
          .map(term -> term.split("="))
          .filter(keyValue -> keyValue.length == 2)
          .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1])));
    }

    this.urlEncoder = new URLEncoder(StandardCharsets.UTF_8);
  }

  /**
   * Set a scheme.
   * 
   * @param scheme
   * @return
   */
  public URIBuilderTiny setScheme(@NonNull String scheme) {
    this.scheme = scheme;
    return this;
  }

  /**
   * Set a host.
   * 
   * This method applies percent-encoding to host automatically.
   * 
   * @param host
   * @return
   */
  public URIBuilderTiny setHost(@NonNull String host) {
    boolean isTrailingSlash = false;
    final int hostLength = host.length();
    if (host.substring(hostLength - 1).equals("/")) { // is last character slash?
      host = host.substring(0, hostLength - 1);
      isTrailingSlash = true;
    }

    this.host = urlEncoder.encode(host);
    if (isTrailingSlash) {
      this.host += "/";
    }
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
  public URIBuilderTiny setPort(int port) {
    this.port = port;
    return this;
  }

  /**
   * Set paths.
   * 
   * Replace current paths with argument. This method applies percent-encoding to paths
   * automatically.
   * 
   * @param paths
   * @return
   */
  public URIBuilderTiny setPaths(@NonNull List<String> paths) {
    this.paths.clear();
    this.paths.addAll(urlEncoder.encode(paths));
    return this;
  }

  /**
   * Set paths.
   * 
   * Replace current paths with argument. This method applies percent-encoding to paths
   * automatically.
   * 
   * @param paths
   * @return
   */
  public URIBuilderTiny setPaths(@NonNull String... paths) {
    this.paths.clear();
    this.paths.addAll(urlEncoder.encode(Arrays.asList(paths)));
    return this;
  }

  /**
   * Set paths by string.
   *
   * It splits paths string by "/" and replace paths with them. This method applies percent-encoding
   * to paths automatically.
   *
   * @param paths
   * @return
   */
  public URIBuilderTiny setPathsByString(@NonNull String paths) {
    this.paths.clear();
    this.paths.addAll(urlEncoder.encode(Arrays.asList(paths.split("/"))));
    return this;
  }

  /**
   * Append paths to current paths.
   * 
   * This method applies percent-encoding to paths automatically.
   * 
   * @param paths
   * @return
   */
  public URIBuilderTiny appendPaths(@NonNull List<String> paths) {
    this.paths.addAll(urlEncoder.encode(paths));
    return this;
  }

  /**
   * Append paths to current paths.
   * 
   * This method applies percent-encoding to paths automatically.
   * 
   * @param paths
   * @return
   */
  public URIBuilderTiny appendPaths(@NonNull String... paths) {
    this.paths.addAll(urlEncoder.encode(Arrays.asList(paths)));
    return this;
  }

  /**
   * Append paths to current paths by string.
   *
   * It splits paths string by "/" and append them to current paths. This method applies
   * percent-encoding to paths automatically.
   *
   * @param paths
   * @return
   */
  public URIBuilderTiny appendPathsByString(@NonNull String paths) {
    this.paths.addAll(urlEncoder.encode(Arrays.asList(paths.split("/"))));
    return this;
  }

  /**
   * Set query parameters.
   * 
   * Replace current query parameters with argument. This method applies percent-encoding to query
   * parameters automatically.
   * 
   * @param queryParameters
   * @return
   */
  public URIBuilderTiny setQueryParameters(@NonNull Map<String, String> queryParameters) {
    this.queryParameters.clear();
    this.queryParameters.putAll(urlEncoder.encode(queryParameters));
    return this;
  }

  /**
   * Set a query parameter.
   * 
   * Replace current query parameter with argument. This method applies percent-encoding to a query
   * parameter automatically.
   * 
   * @param key
   * @param value
   * @return
   */
  public URIBuilderTiny setQueryParameter(@NonNull String key, @NonNull String value) {
    this.queryParameters.clear();
    this.queryParameters.put(urlEncoder.encode(key), urlEncoder.encode(value));
    return this;
  }

  /**
   * Add query parameters.
   * 
   * This method applies percent-encoding to query parameters automatically.
   * 
   * @param queryParameters
   * @return
   */
  public URIBuilderTiny addQueryParameters(@NonNull Map<String, String> queryParameters) {
    this.queryParameters.putAll(urlEncoder.encode(queryParameters));
    return this;
  }

  /**
   * Add a query parameter.
   * 
   * This method applies percent-encoding to a query parameter automatically.
   * 
   * @param key
   * @param value
   * @return
   */
  public URIBuilderTiny addQueryParameter(@NonNull String key, @NonNull String value) {
    this.queryParameters.put(urlEncoder.encode(key), urlEncoder.encode(value));
    return this;
  }

  /**
   * Set a fragment.
   * 
   * This method applies percent-encoding to a fragment automatically.
   * 
   * @param fragment
   * @return
   */
  public URIBuilderTiny setFragment(@NonNull String fragment) {
    this.fragment = urlEncoder.encode(fragment);
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

    boolean shouldAppendTrailingSlash = false;
    if (!host.isEmpty()) {
      if (host.substring(host.length() - 1).equals("/")) { // is last character slash?
        shouldAppendTrailingSlash = true;
        host = host.substring(0, host.length() - 1);
      }
      uriStringBuilder.append(host);
    }

    if (port >= 0) {
      uriStringBuilder.append(":").append(port);
    }

    if (paths.isEmpty()) {
      if (shouldAppendTrailingSlash) {
        uriStringBuilder.append("/");
      }
    } else {
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
    if (!scheme.isEmpty()) {
      uriString = scheme + "://" + uriString;
    }

    return new URI(uriString);
  }
}
