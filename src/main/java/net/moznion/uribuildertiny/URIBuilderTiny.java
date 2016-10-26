package net.moznion.uribuildertiny;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.NonNull;

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
    @Getter
    private boolean forceRemoveTrailingSlash;

    private final URLEncoder urlEncoder;
    private final URLEncoder nopURLEncoder;

    /**
     * Create a new empty instance.
     */
    public URIBuilderTiny() {
        this(URI.create(""));
    }

    /**
     * Create a new instance according to passed URI string.
     * <p>
     * This method doesn't apply percent-encoding to URI string which is passed via argument.
     */
    public URIBuilderTiny(@NonNull String uriString) {
        this(URI.create(uriString));
    }

    /**
     * Create a new instance according to passed URI instance.
     * <p>
     * This method doesn't apply percent-encoding to URI which is passed via argument.
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
            for (String term : queryString.split("&")) {
                final String[] kv = term.split("=");
                if (kv.length == 2) {
                    queryParameters.put(kv[0], kv[1]);
                }
            }
        }

        urlEncoder = new URLEncoder(new ConcreteEntityURLEncoder(StandardCharsets.UTF_8));
        nopURLEncoder = new URLEncoder(new NopEntityURLEncoder());

        forceRemoveTrailingSlash = false;
    }

    /**
     * Set a scheme.
     */
    public URIBuilderTiny setScheme(@NonNull String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * Set a host.
     * <p>
     * This method applies percent-encoding to host automatically.
     */
    public URIBuilderTiny setHost(@NonNull String host) {
        return setHost(urlEncoder, host);
    }

    /**
     * Set a host as raw string.
     */
    public URIBuilderTiny setRawHost(@NonNull String host) {
        return setHost(nopURLEncoder, host);
    }

    private URIBuilderTiny setHost(URLEncoder urlEncoder, @NonNull String host) {
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
     * <p>
     * If you pass a negative value to this argument, this builder deals as port isn't specified.
     */
    public URIBuilderTiny setPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Set paths.
     * <p>
     * Replace current paths with argument. This method applies percent-encoding to paths
     * automatically.
     */
    public URIBuilderTiny setPaths(@NonNull List<?> paths) {
        return setPaths(urlEncoder, paths);
    }

    /**
     * Set paths as raw string.
     */
    public URIBuilderTiny setRawPaths(@NonNull List<?> paths) {
        return setPaths(nopURLEncoder, paths);
    }

    private URIBuilderTiny setPaths(URLEncoder urlEncoder, @NonNull List<?> paths) {
        this.paths.clear();
        this.paths.addAll(urlEncoder.encode(paths));
        return this;
    }

    /**
     * Set paths.
     * <p>
     * Replace current paths with argument. This method applies percent-encoding to paths
     * automatically.
     */
    public URIBuilderTiny setPaths(@NonNull Object... paths) {
        return setPaths(urlEncoder, paths);
    }

    /**
     * Set paths as raw string.
     */
    public URIBuilderTiny setRawPaths(@NonNull Object... paths) {
        return setPaths(nopURLEncoder, paths);
    }

    private URIBuilderTiny setPaths(URLEncoder urlEncoder, @NonNull Object... paths) {
        this.paths.clear();
        this.paths.addAll(urlEncoder.encode(Arrays.asList(paths)));
        return this;
    }

    /**
     * Set paths by string.
     * <p>
     * It splits paths string by "/" and replace paths with them. This method applies percent-encoding
     * to paths automatically.
     */
    public URIBuilderTiny setPathsByString(@NonNull String paths) {
        return setPathsByString(urlEncoder, paths);
    }

    /**
     * Set paths by string. It will be treated as raw string.
     */
    public URIBuilderTiny setRawPathsByString(@NonNull String paths) {
        return setPathsByString(nopURLEncoder, paths);
    }

    private URIBuilderTiny setPathsByString(URLEncoder urlEncoder, @NonNull String paths) {
        this.paths.clear();
        this.paths.addAll(urlEncoder.encode(Arrays.asList(paths.split("/"))));
        return this;
    }

    /**
     * Append paths to current paths.
     * <p>
     * This method applies percent-encoding to paths automatically.
     */
    public URIBuilderTiny appendPaths(@NonNull List<?> paths) {
        return appendPaths(urlEncoder, paths);
    }

    /**
     * Append paths to current paths as raw string.
     */
    public URIBuilderTiny appendRawPaths(@NonNull List<?> paths) {
        return appendPaths(nopURLEncoder, paths);
    }

    private URIBuilderTiny appendPaths(URLEncoder urlEncoder, @NonNull List<?> paths) {
        this.paths.addAll(urlEncoder.encode(paths));
        return this;
    }

    /**
     * Append paths to current paths.
     * <p>
     * This method applies percent-encoding to paths automatically.
     */
    public URIBuilderTiny appendPaths(@NonNull Object... paths) {
        return appendPaths(urlEncoder, paths);
    }

    /**
     * Append paths to current paths as raw string.
     */
    public URIBuilderTiny appendRawPaths(@NonNull Object... paths) {
        return appendPaths(nopURLEncoder, paths);
    }

    private URIBuilderTiny appendPaths(URLEncoder urlEncoder, @NonNull Object... paths) {
        this.paths.addAll(urlEncoder.encode(Arrays.asList(paths)));
        return this;
    }

    /**
     * Append paths to current paths by string.
     * <p>
     * It splits paths string by "/" and append them to current paths. This method applies
     * percent-encoding to paths automatically.
     */
    public URIBuilderTiny appendPathsByString(@NonNull String paths) {
        return appendPathsByString(urlEncoder, paths);
    }

    /**
     * Append paths to current paths by string. It will be treated as raw string.
     */
    public URIBuilderTiny appendRawPathsByString(@NonNull String paths) {
        return appendPathsByString(nopURLEncoder, paths);
    }

    private URIBuilderTiny appendPathsByString(URLEncoder urlEncoder, @NonNull String paths) {
        this.paths.addAll(urlEncoder.encode(Arrays.asList(paths.split("/"))));
        return this;
    }

    /**
     * Set query parameters.
     * <p>
     * Replace current query parameters with argument. This method applies percent-encoding to query
     * parameters automatically.
     */
    public <T> URIBuilderTiny setQueryParameters(@NonNull Map<String, T> queryParameters) {
        return setQueryParameters(urlEncoder, queryParameters);
    }

    /**
     * Set query parameters as raw string.
     */
    public <T> URIBuilderTiny setRawQueryParameters(@NonNull Map<String, T> queryParameters) {
        return setQueryParameters(nopURLEncoder, queryParameters);
    }

    private <T> URIBuilderTiny setQueryParameters(URLEncoder urlEncoder,
                                                  @NonNull Map<String, T> queryParameters) {
        this.queryParameters.clear();
        this.queryParameters.putAll(urlEncoder.encode(queryParameters));
        return this;
    }

    /**
     * Set a query parameter.
     * <p>
     * Replace current query parameter with argument. This method applies percent-encoding to a query
     * parameter automatically.
     */
    public URIBuilderTiny setQueryParameter(@NonNull String key, @NonNull Object value) {
        return setQueryParameter(urlEncoder, key, value);
    }

    /**
     * Set query parameter as raw string.
     */
    public URIBuilderTiny setRawQueryParameter(@NonNull String key, @NonNull Object value) {
        return setQueryParameter(nopURLEncoder, key, value);
    }

    private URIBuilderTiny setQueryParameter(URLEncoder urlEncoder,
                                             @NonNull String key,
                                             @NonNull Object value) {
        queryParameters.clear();
        queryParameters.put(urlEncoder.encode(key), urlEncoder.encode(value));
        return this;
    }

    /**
     * Add query parameters.
     * <p>
     * This method applies percent-encoding to query parameters automatically.
     */
    public URIBuilderTiny addQueryParameters(@NonNull Map<String, ?> queryParameters) {
        return addQueryParameters(urlEncoder, queryParameters);
    }

    /**
     * Add query parameter as raw string.
     */
    public URIBuilderTiny addRawQueryParameters(@NonNull Map<String, ?> queryParameters) {
        return addQueryParameters(nopURLEncoder, queryParameters);
    }

    private URIBuilderTiny addQueryParameters(URLEncoder urlEncoder, @NonNull Map<String, ?> queryParameters) {
        this.queryParameters.putAll(urlEncoder.encode(queryParameters));
        return this;
    }

    /**
     * Add a query parameter.
     * <p>
     * This method applies percent-encoding to a query parameter automatically.
     */
    public URIBuilderTiny addQueryParameter(@NonNull String key, @NonNull Object value) {
        return addQueryParameter(urlEncoder, key, value);
    }

    /**
     * Add a query parameter as raw string.
     */
    public URIBuilderTiny addRawQueryParameter(@NonNull String key, @NonNull Object value) {
        return addQueryParameter(nopURLEncoder, key, value);
    }

    private URIBuilderTiny addQueryParameter(URLEncoder urlEncoder,
                                             @NonNull String key,
                                             @NonNull Object value) {
        queryParameters.put(urlEncoder.encode(key), urlEncoder.encode(value));
        return this;
    }

    /**
     * Set a fragment.
     * <p>
     * This method applies percent-encoding to a fragment automatically.
     */
    public URIBuilderTiny setFragment(@NonNull String fragment) {
        return setFragment(urlEncoder, fragment);
    }

    /**
     * Set a fragment as raw string.
     */
    public URIBuilderTiny setRawFragment(@NonNull String fragment) {
        return setFragment(nopURLEncoder, fragment);
    }

    private URIBuilderTiny setFragment(URLEncoder urlEncoder, @NonNull String fragment) {
        this.fragment = urlEncoder.encode(fragment);
        return this;
    }

    /**
     * Set flag to decide to remove trailing slash.
     * <p>
     * This URI builder automatically append a trailing slash if part of host has that.
     * So if you want to disable such function, please pass true value to this method.
     */
    public URIBuilderTiny forceRemoveTrailingSlash(boolean shouldRemove) {
        forceRemoveTrailingSlash = shouldRemove;
        return this;
    }

    private static final Pattern CONSECUTIVE_SLASHES_RE = Pattern.compile("/+");

    /**
     * Build a new URI instance by according to builder's information.
     */
    public URI build() {
        StringBuilder uriStringBuilder = new StringBuilder();

        boolean shouldAppendTrailingSlash = false;
        if (!host.isEmpty()) {
            if (host.charAt(host.length() - 1) == '/') { // is last character slash?
                shouldAppendTrailingSlash = !forceRemoveTrailingSlash;
                host = host.substring(0, host.length() - 1);
            }
            uriStringBuilder.append(host);
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
        }

        if (shouldAppendTrailingSlash) {
            uriStringBuilder.append("/");
        }

        if (!queryParameters.isEmpty()) {
            uriStringBuilder.append("?");

            int i = 1;
            final int size = queryParameters.size();
            for (Entry<String, String> queryParameter : queryParameters.entrySet()) {
                uriStringBuilder.append(queryParameter.getKey()).append("=").append(queryParameter.getValue());
                if (i != size) {
                    uriStringBuilder.append("&");
                }
                i++;
            }
        }

        if (!fragment.isEmpty()) {
            uriStringBuilder.append("#").append(fragment);
        }

        String uriString = uriStringBuilder.toString();

        uriString = CONSECUTIVE_SLASHES_RE.matcher(uriString).replaceAll("/"); // Squash consecutive
        // slashes
        if (!scheme.isEmpty()) {
            String glue = "://";
            if (uriString.charAt(0) == '/') {
                glue = ":/"; // the second slash for scheme exists in uriString, so reduced
            }
            uriString = scheme + glue + uriString;
        }

        return URI.create(uriString);
    }
}
