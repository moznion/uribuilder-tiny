package net.moznion.uribuildertiny;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class URIBuilderTinyTest {
    @Test
    public void shouldGetURIInstanceByManual() throws URISyntaxException {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("hoge", "fuga");

        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setPort(8080)
                .setPaths(Arrays.asList("foo", "bar"))
                .appendPaths(Arrays.asList("buz", "qux"))
                .setQueryParameters(queryParameters)
                .addQueryParameter("piyo", "hogera")
                .setFragment("frag")
                .build();
        assertEquals("https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag",
                got.toString());
    }

    @Test
    public void shouldGetURIInstanceByStringInitialValue() throws URISyntaxException {
        URI got = new URIBuilderTiny("https://java.example.com/foo/bar")
                .setPort(8080)
                .appendPaths(Arrays.asList("buz", "qux"))
                .addQueryParameter("hoge", "fuga")
                .addQueryParameter("piyo", "hogera")
                .setFragment("frag")
                .build();
        assertEquals("https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag",
                got.toString());
    }

    @Test
    public void shouldGetURIInstanceByURIInitialValue() throws URISyntaxException {
        URI got = new URIBuilderTiny(new URI("https://java.example.com/foo/bar"))
                .setPort(8080)
                .appendPaths(Arrays.asList("buz", "qux"))
                .addQueryParameter("hoge", "fuga")
                .addQueryParameter("piyo", "hogera")
                .setFragment("frag")
                .build();
        assertEquals("https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag",
                got.toString());
    }

    @Test
    public void shouldGetURIInstanceWithEmptyURI() throws URISyntaxException {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("hoge", "fuga");

        URI got = new URIBuilderTiny(new URI(""))
                .setScheme("https")
                .setHost("java.example.com")
                .setPort(8080)
                .setPaths(Arrays.asList("foo", "bar"))
                .appendPaths(Arrays.asList("buz", "qux"))
                .setQueryParameters(queryParameters)
                .addQueryParameter("piyo", "hogera")
                .setFragment("frag")
                .build();
        assertEquals("https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag",
                got.toString());
    }

    @Test
    public void testSetByStringy() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setPort(8080)
                .setPaths(Arrays.asList("foo", "bar"))
                .appendPaths(Arrays.asList("buz", "qux"))
                .build();
        assertEquals("http://java.example.com:8080/foo/bar/buz/qux", got.toString());
    }

    @Test
    public void testEmptyPort() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .build();
        assertEquals("http://java.example.com", got.toString());
    }

    @Test
    public void testSetAndAddQueryParamWithStringPair() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setQueryParameter("hoge", "fuga")
                .addQueryParameter("piyo", "piyopiyo")
                .build();
        assertEquals("http://java.example.com?hoge=fuga&piyo=piyopiyo", got.toString());
    }

    @Test
    public void testSetAndAddQueryParamsWithMap() throws URISyntaxException {
        Map<String, String> initQueryParameters = new HashMap<>();
        initQueryParameters.put("hoge", "fuga");

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("piyo", "piyopiyo");

        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setQueryParameters(initQueryParameters)
                .addQueryParameters(queryParameters)
                .build();
        assertEquals("http://java.example.com?hoge=fuga&piyo=piyopiyo", got.toString());
    }

    @Test
    public void testForTrailingSlash() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com/")
                .setPort(8080)
                .build();
        assertEquals("http://java.example.com:8080/", got.toString());
    }

    @Test
    public void testForTrailingSlashWithPaths() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com/")
                .setPort(8080)
                .setPaths(Arrays.asList("foo", "bar"))
                .build();
        assertEquals("http://java.example.com:8080/foo/bar/", got.toString());
    }

    @Test
    public void testEmpty() throws URISyntaxException {
        URI got = new URIBuilderTiny("").build();
        assertEquals("", got.toString());
    }

    @Test
    public void testForQueryStringOfInitValue() throws URISyntaxException {
        URI got = new URIBuilderTiny("http://java.example.com?foo=bar&buz=qux")
                .setPort(8080)
                .build();
        assertEquals("http://java.example.com:8080?buz=qux&foo=bar", got.toString());
    }

    @Test
    public void testForAmbiguousQueryStringOfInitValue() throws URISyntaxException {
        URI got = new URIBuilderTiny("http://java.example.com?foo&buz=qux")
                .setPort(8080)
                .build();
        assertEquals("http://java.example.com:8080?buz=qux", got.toString());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoConstructorByString() throws URISyntaxException {
        new URIBuilderTiny((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoConstructorByURI() throws URISyntaxException {
        new URIBuilderTiny((URI) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoSetScheme() throws URISyntaxException {
        new URIBuilderTiny().setScheme(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoSetHost() throws URISyntaxException {
        new URIBuilderTiny().setHost(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoSetPaths() throws URISyntaxException {
        new URIBuilderTiny().setPaths((List<String>) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoAppendPaths() throws URISyntaxException {
        new URIBuilderTiny().appendPaths((List<String>) null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoSetQueryParameters() throws URISyntaxException {
        new URIBuilderTiny().setQueryParameters(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoSetQueryParameterOfKey() throws URISyntaxException {
        new URIBuilderTiny().setQueryParameter(null, "value");
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoSetQueryParameterOfValue() throws URISyntaxException {
        new URIBuilderTiny().setQueryParameter("key", null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoAddQueryParameters() throws URISyntaxException {
        new URIBuilderTiny().addQueryParameters(null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoAddQueryParameterOfKey() throws URISyntaxException {
        new URIBuilderTiny().addQueryParameter(null, "value");
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoAddQueryParameterOfValue() throws URISyntaxException {
        new URIBuilderTiny().addQueryParameter("key", null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoSetFragment() throws URISyntaxException {
        new URIBuilderTiny().setFragment(null);
    }

    @Test
    public void testToOverwritePath() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setPaths(Arrays.asList("foo", "bar"))
                .setPaths(Arrays.asList("buz", "qux"))
                .build();
        assertEquals("https://java.example.com/buz/qux", got.toString());
    }

    @Test
    public void testToOverwriteQueryParam() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setQueryParameter("foo", "bar")
                .setQueryParameter("buz", "qux")
                .build();
        assertEquals("https://java.example.com?buz=qux", got.toString());
    }

    @Test
    public void testToOverwriteQueryParams() throws URISyntaxException {
        Map<String, String> oldParam = new HashMap<>();
        oldParam.put("foo", "bar");
        Map<String, String> newParam = new HashMap<>();
        newParam.put("buz", "qux");

        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setQueryParameters(oldParam)
                .setQueryParameters(newParam)
                .build();
        assertEquals("https://java.example.com?buz=qux", got.toString());
    }

    @Test
    public void testForGetters() throws URISyntaxException {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("hoge", "fuga");

        URIBuilderTiny b = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setPort(8080)
                .setPaths(Arrays.asList("foo", "bar"))
                .appendPaths(Arrays.asList("buz", "qux"))
                .setQueryParameters(queryParameters)
                .addQueryParameter("piyo", "hogera")
                .setFragment("frag");

        Map<String, String> expectedQueryParams = new TreeMap<>();
        expectedQueryParams.put("hoge", "fuga");
        expectedQueryParams.put("piyo", "hogera");

        assertEquals("https", b.getScheme());
        assertEquals("java.example.com", b.getHost());
        assertEquals(8080, b.getPort());
        assertEquals(Arrays.asList("foo", "bar", "buz", "qux"), b.getPaths());
        assertEquals(expectedQueryParams, b.getQueryParameters());
        assertEquals("frag", b.getFragment());
    }

    @Test
    public void testForVarargSetPaths() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setPort(8080)
                .setPaths("foo", "bar")
                .build();
        assertEquals("http://java.example.com:8080/foo/bar", got.toString());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoVarargSetPaths() throws URISyntaxException {
        new URIBuilderTiny().setPaths((String[]) null);
    }

    @Test
    public void testForVarargAppendPaths() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setPort(8080)
                .setPaths("foo", "bar")
                .appendPaths("qux", "quux")
                .build();
        assertEquals("http://java.example.com:8080/foo/bar/qux/quux", got.toString());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNPEWhenPassNullIntoVarargAppendPaths() throws URISyntaxException {
        new URIBuilderTiny().appendPaths((String[]) null);
    }

    @Test
    public void testForSettingAndAppendingPathsByString() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setPort(8080)
                .setPathsByString("/foo/bar")
                .appendPathsByString("/qux/quux")
                .build();
        assertEquals("http://java.example.com:8080/foo/bar/qux/quux", got.toString());
    }

    @Test
    public void testForSchemeAndPathsCase() throws URISyntaxException {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .appendPaths("foo", "bar")
                .build();
        assertEquals("http://foo/bar", got.toString());
    }
}
