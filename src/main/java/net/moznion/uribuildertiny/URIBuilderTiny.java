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
     * <p>
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
     * <p>
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
            for (String term : queryString.split("&")) {
                final String[] kv = term.split("=");
                if (kv.length == 2) {
                    queryParameters.put(kv[0], kv[1]);
                }
            }
        }

        this.urlEncoder = new URLEncoder(StandardCharsets.UTF_8);

        this.forceRemoveTrailingSlash = false;
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
     * <p>
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
     * <p>
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
     * <p>
     * Replace current paths with argument. This method applies percent-encoding to paths
     * automatically.
     *
     * @param paths
     * @return
     */
    public <T> URIBuilderTiny setPaths(@NonNull List<T> paths) {
        this.paths.clear();
        this.paths.addAll(urlEncoder.encode(paths));
        return this;
    }

    /**
     * Set paths.
     * <p>
     * Replace current paths with argument. This method applies percent-encoding to paths
     * automatically.
     *
     * @param paths
     * @return
     */
    public <T> URIBuilderTiny setPaths(@NonNull T... paths) {
        this.paths.clear();
        this.paths.addAll(urlEncoder.encode(Arrays.asList(paths)));
        return this;
    }

    /**
     * Set paths by string.
     * <p>
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
     * <p>
     * This method applies percent-encoding to paths automatically.
     *
     * @param paths
     * @return
     */
    public <T> URIBuilderTiny appendPaths(@NonNull List<T> paths) {
        this.paths.addAll(urlEncoder.encode(paths));
        return this;
    }

    /**
     * Append paths to current paths.
     * <p>
     * This method applies percent-encoding to paths automatically.
     *
     * @param paths
     * @return
     */
    public <T> URIBuilderTiny appendPaths(@NonNull T... paths) {
        this.paths.addAll(urlEncoder.encode(Arrays.asList(paths)));
        return this;
    }

    /**
     * Append paths to current paths by string.
     * <p>
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
     * <p>
     * Replace current query parameters with argument. This method applies percent-encoding to query
     * parameters automatically.
     *
     * @param queryParameters
     * @return
     */
    public <T> URIBuilderTiny setQueryParameters(@NonNull Map<String, T> queryParameters) {
        this.queryParameters.clear();
        this.queryParameters.putAll(urlEncoder.encode(queryParameters));
        return this;
    }

    /**
     * Set a query parameter.
     * <p>
     * Replace current query parameter with argument. This method applies percent-encoding to a query
     * parameter automatically.
     *
     * @param key
     * @param value
     * @return
     */
    public <T> URIBuilderTiny setQueryParameter(@NonNull String key, @NonNull T value) {
        this.queryParameters.clear();
        this.queryParameters.put(urlEncoder.encode(key), urlEncoder.encode(value));
        return this;
    }

    /**
     * Add query parameters.
     * <p>
     * This method applies percent-encoding to query parameters automatically.
     *
     * @param queryParameters
     * @return
     */
    public <T> URIBuilderTiny addQueryParameters(@NonNull Map<String, T> queryParameters) {
        this.queryParameters.putAll(urlEncoder.encode(queryParameters));
        return this;
    }

    /**
     * Add a query parameter.
     * <p>
     * This method applies percent-encoding to a query parameter automatically.
     *
     * @param key
     * @param value
     * @return
     */
    public <T> URIBuilderTiny addQueryParameter(@NonNull String key, @NonNull T value) {
        this.queryParameters.put(urlEncoder.encode(key), urlEncoder.encode(value));
        return this;
    }

    /**
     * Set a fragment.
     * <p>
     * This method applies percent-encoding to a fragment automatically.
     *
     * @param fragment
     * @return
     */
    public URIBuilderTiny setFragment(@NonNull String fragment) {
        this.fragment = urlEncoder.encode(fragment);
        return this;
    }

    /**
     * Set flag to decide to remove trailing slash.
     * <p>
     * This URI builder automatically append a trailing slash if part of host has that.
     * So if you want to disable such function, please pass true value to this method.
     *
     * @param shouldRemove
     * @return
     */
    public URIBuilderTiny forceRemoveTrailingSlash(boolean shouldRemove) {
        this.forceRemoveTrailingSlash = shouldRemove;
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

        return new URI(uriString);
    }
}
