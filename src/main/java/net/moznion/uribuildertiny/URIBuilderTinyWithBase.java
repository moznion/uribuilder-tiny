package net.moznion.uribuildertiny;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Provider of {@link URIBuilderTiny} with base URI.
 */
public class URIBuilderTinyWithBase {
    private final String baseUriString;

    /**
     * Create instance with base URI string.
     *
     * @param baseUriString base URI string
     */
    public URIBuilderTinyWithBase(String baseUriString) {
        this.baseUriString = baseUriString;
    }

    /**
     * Create instance with base URI.
     *
     * @param baseUri base URI
     */
    public URIBuilderTinyWithBase(URI baseUri) {
        baseUriString = baseUri.toString();
    }

    /**
     * Provide an instance of {@link URIBuilderTiny}.
     *
     * @return an instance of {@link URIBuilderTiny}
     * @throws URISyntaxException
     */
    public URIBuilderTiny getBuilder() throws URISyntaxException {
        return new URIBuilderTiny(baseUriString);
    }
}
