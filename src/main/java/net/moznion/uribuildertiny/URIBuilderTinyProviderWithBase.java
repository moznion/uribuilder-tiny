package net.moznion.uribuildertiny;

import java.net.URI;

/**
 * Provider of {@link URIBuilderTiny} with base URI.
 */
public class URIBuilderTinyProviderWithBase {
    private final String baseUriString;

    /**
     * Create instance with base URI string.
     *
     * @param baseUriString base URI string
     */
    public URIBuilderTinyProviderWithBase(String baseUriString) {
        this.baseUriString = baseUriString;
    }

    /**
     * Create instance with base URI.
     *
     * @param baseUri base URI
     */
    public URIBuilderTinyProviderWithBase(URI baseUri) {
        baseUriString = baseUri.toString();
    }

    /**
     * Provide an instance of {@link URIBuilderTiny}.
     *
     * @return an instance of {@link URIBuilderTiny}
     */
    public URIBuilderTiny getBuilder() {
        return new URIBuilderTiny(baseUriString);
    }
}
