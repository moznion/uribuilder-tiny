package net.moznion.uribuildertiny;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class URIBuilderTinyProviderWithBaseTest {
    @Test
    public void shouldGetBuilderWithBaseURIString() throws URISyntaxException {
        URIBuilderTinyProviderWithBase withBase = new URIBuilderTinyProviderWithBase("http://example.com");

        {
            URIBuilderTiny ubt = withBase.getBuilder().appendPaths("foo");
            assertEquals("http://example.com/foo", ubt.build().toString());
        }

        {
            URIBuilderTiny ubt = withBase.getBuilder().appendPaths("bar");
            assertEquals("http://example.com/bar", ubt.build().toString());
        }
    }

    @Test
    public void shouldGetBuilderWithBaseURI() throws URISyntaxException {
        URIBuilderTinyProviderWithBase withBase = new URIBuilderTinyProviderWithBase(new URI("http://example.com"));

        {
            URIBuilderTiny ubt = withBase.getBuilder().appendPaths("foo");
            assertEquals("http://example.com/foo", ubt.build().toString());
        }

        {
            URIBuilderTiny ubt = withBase.getBuilder().appendPaths("bar");
            assertEquals("http://example.com/bar", ubt.build().toString());
        }
    }
}
