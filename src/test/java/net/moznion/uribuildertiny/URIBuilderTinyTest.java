package net.moznion.uribuildertiny;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

public class URIBuilderTinyTest {
    @Test
    public void shouldGetURIInstanceByManual() {
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
    public void shouldGetURIInstanceByStringInitialValue() {
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
    public void shouldGetURIInstanceByURIInitialValue() {
        URI got = new URIBuilderTiny(URI.create("https://java.example.com/foo/bar"))
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
    public void shouldGetURIInstanceWithEmptyURI() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("hoge", "fuga");

        URI got = new URIBuilderTiny(URI.create(""))
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
    public void testSetByStringy() {
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
    public void testEmptyPort() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .build();
        assertEquals("http://java.example.com", got.toString());
    }

    @Test
    public void testSetAndAddQueryParamWithStringPair() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setQueryParameter("hoge", "fuga")
                .addQueryParameter("piyo", "piyopiyo")
                .build();
        assertEquals("http://java.example.com?hoge=fuga&piyo=piyopiyo", got.toString());
    }

    @Test
    public void testSetAndAddQueryParamsWithMap() {
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
    public void testForTrailingSlash() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com/")
                .setPort(8080)
                .build();
        assertEquals("http://java.example.com:8080/", got.toString());
    }

    @Test
    public void testForTrailingSlashWithPaths() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com/")
                .setPort(8080)
                .setPaths(Arrays.asList("foo", "bar"))
                .build();
        assertEquals("http://java.example.com:8080/foo/bar/", got.toString());
    }

    @Test
    public void testEmpty() {
        URI got = new URIBuilderTiny("").build();
        assertEquals("", got.toString());
    }

    @Test
    public void testForQueryStringOfInitValue() {
        URI got = new URIBuilderTiny("http://java.example.com?foo=bar&buz=qux")
                .setPort(8080)
                .build();
        assertEquals("http://java.example.com:8080?buz=qux&foo=bar", got.toString());
    }

    @Test
    public void testForAmbiguousQueryStringOfInitValue() {
        URI got = new URIBuilderTiny("http://java.example.com?foo&buz=qux")
                .setPort(8080)
                .build();
        assertEquals("http://java.example.com:8080?buz=qux", got.toString());
    }

    @Test
    public void shouldNPEWhenPassNullIntoConstructorByString() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny((String) null));

    }

    @Test
    public void shouldNPEWhenPassNullIntoConstructorByURI() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny((URI) null));

    }

    @Test
    public void shouldNPEWhenPassNullIntoSetScheme() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().setScheme(null));
    }

    @Test
    public void shouldNPEWhenPassNullIntoSetHost() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().setHost(null));
    }

    @Test
    public void shouldNPEWhenPassNullIntoSetPaths() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().setPaths((List<Object>) null));
    }

    @Test
    public void shouldNPEWhenPassNullIntoAppendPaths() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().appendPaths((List<String>) null));
    }

    @Test
    public void shouldNPEWhenPassNullIntoSetQueryParameters() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().setQueryParameters(null));
    }

    @Test
    public void shouldNPEWhenPassNullIntoSetQueryParameterOfKey() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().setQueryParameter(null, "value"));
    }

    @Test
    public void shouldNPEWhenPassNullIntoSetQueryParameterOfValue() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().setQueryParameter("key", null));
    }

    @Test
    public void shouldNPEWhenPassNullIntoAddQueryParameters() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().addQueryParameters(null));
    }

    @Test
    public void shouldNPEWhenPassNullIntoAddQueryParameterOfKey() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().addQueryParameter(null, "value"));
    }

    @Test
    public void shouldNPEWhenPassNullIntoAddQueryParameterOfValue() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().addQueryParameter("key", null));
    }

    @Test
    public void shouldNPEWhenPassNullIntoSetFragment() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().setFragment(null));
    }

    @Test
    public void testToOverwritePath() {
        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setPaths(Arrays.asList("foo", "bar"))
                .setPaths(Arrays.asList("buz", "qux"))
                .build();
        assertEquals("https://java.example.com/buz/qux", got.toString());
    }

    @Test
    public void testToOverwriteQueryParam() {
        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setQueryParameter("foo", "bar")
                .setQueryParameter("buz", "qux")
                .build();
        assertEquals("https://java.example.com?buz=qux", got.toString());
    }

    @Test
    public void testToOverwriteQueryParams() {
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
    public void testForGetters() {
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
    public void testForVarargSetPaths() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setPort(8080)
                .setPaths("foo", "bar")
                .build();
        assertEquals("http://java.example.com:8080/foo/bar", got.toString());
    }

    @Test
    public void shouldNPEWhenPassNullIntoVarargSetPaths() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().setPaths((Object[]) null));
    }

    @Test
    public void testForVarargAppendPaths() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com")
                .setPort(8080)
                .setPaths("foo", "bar")
                .appendPaths("qux", "quux")
                .build();
        assertEquals("http://java.example.com:8080/foo/bar/qux/quux", got.toString());
    }

    @Test
    public void shouldNPEWhenPassNullIntoVarargAppendPaths() {
        assertThrows(NullPointerException.class, () -> new URIBuilderTiny().appendPaths((Object[]) null));
    }

    @Test
    public void testForSettingAndAppendingPathsByString() {
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
    public void testForSchemeAndPathsCase() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .appendPaths("foo", "bar")
                .build();
        assertEquals("http://foo/bar", got.toString());
    }

    @Test
    public void shouldSetPathsEvenIfAnyTypeByList() {
        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setPaths(Arrays.asList("foo", 1, (long) 100, true, new Foo("bar")))
                .build();
        assertEquals("https://java.example.com/foo/1/100/true/bar", got.toString());
    }

    @Test
    public void shouldSetPathsEvenIfAnyTypeByVararg() {
        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setPaths("foo", 1, (long) 100, true, new Foo("bar"))
                .build();
        assertEquals("https://java.example.com/foo/1/100/true/bar", got.toString());
    }

    @Test
    public void shouldAppendPathsEvenIfAnyTypeByList() {
        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .appendPaths(Arrays.asList("foo", 1, (long) 100, true, new Foo("bar")))
                .build();
        assertEquals("https://java.example.com/foo/1/100/true/bar", got.toString());
    }

    @Test
    public void shouldAppendPathsEvenIfAnyTypeByVararg() {
        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.example.com")
                .setPaths("foo", 1, (long) 100, true, new Foo("bar"))
                .build();
        assertEquals("https://java.example.com/foo/1/100/true/bar", got.toString());
    }

    @Test
    public void shouldSetQueryParamEvenIfAnyTypeByMap() {
        Map<String, Object> queryParameters = new HashMap<>();
        queryParameters.put("hoge", "fuga");
        queryParameters.put("piyo", 1);
        queryParameters.put("foo", new Foo("foo"));

        URI got = new URIBuilderTiny("http://example.com")
                .setQueryParameters(queryParameters)
                .build();
        assertEquals("http://example.com?foo=foo&hoge=fuga&piyo=1",
                     got.toString());
    }

    @Test
    public void shouldSetQueryParamEvenIfAnyType() {
        URI got = new URIBuilderTiny("http://example.com")
                .setQueryParameter("foo", new Foo("foo"))
                .build();
        assertEquals("http://example.com?foo=foo",
                     got.toString());
    }

    @Test
    public void shouldAddQueryParamEvenIfAnyTypeByMap() {
        Map<String, Object> queryParameters = new HashMap<>();
        queryParameters.put("hoge", "fuga");
        queryParameters.put("piyo", 1);
        queryParameters.put("foo", new Foo("foo"));

        URI got = new URIBuilderTiny("http://example.com")
                .addQueryParameters(queryParameters)
                .build();
        assertEquals("http://example.com?foo=foo&hoge=fuga&piyo=1",
                     got.toString());
    }

    @Test
    public void shouldAddQueryParamEvenIfAnyType() {
        Map<String, Object> queryParameters = new HashMap<>();

        URI got = new URIBuilderTiny("http://example.com")
                .addQueryParameter("hoge", "fuga")
                .addQueryParameter("piyo", 1)
                .addQueryParameter("foo", new Foo("foo"))
                .build();
        assertEquals("http://example.com?foo=foo&hoge=fuga&piyo=1",
                     got.toString());
    }

    @Test
    public void shouldApplyURIEncode() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("h%oge", "f%uga");

        URI got = new URIBuilderTiny()
                .setScheme("https")
                .setHost("java.e%xample.com")
                .setPort(8080)
                .setPaths(Arrays.asList("f%oo", "b%ar"))
                .appendPaths(Arrays.asList("b%uz", "q%ux"))
                .setQueryParameters(queryParameters)
                .addQueryParameter("p%iyo", "h%ogera")
                .setFragment("f%rag")
                .build();
        assertEquals(
                "https://java.e%25xample.com:8080/f%25oo/b%25ar/b%25uz/q%25ux?h%25oge=f%25uga&p%25iyo=h%25ogera#f%25rag",
                got.toString());
    }

    @Test
    public void testForRemovingTrailingSlash() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("java.example.com/")
                .setPort(8080)
                .forceRemoveTrailingSlash(true)
                .build();
        assertEquals("http://java.example.com:8080", got.toString());
    }

    @Test
    public void testSetRawEntities() {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setRawHost("h&ost.example.com")
                .setPort(8080)
                .setRawPaths(Arrays.asList("b&uz", "q&ux"))
                .appendRawPaths(Arrays.asList("f&oobar", "b&uzqux"))
                .setRawQueryParameter("h&oge", "f&uga")
                .addRawQueryParameter("p&iyo", "p&iyopiyo")
                .setRawFragment("f&rag")
                .build();
        assertEquals(
                "http://h&ost.example.com:8080/b&uz/q&ux/f&oobar/b&uzqux?h&oge=f&uga&p&iyo=p&iyopiyo#f&rag",
                got.toString());

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("h&oge", "f&uga");
        queryParameters.put("p&iyo", "p&iyopiyo");

        got = new URIBuilderTiny()
                .setScheme("http")
                .setRawHost("h&ost.example.com")
                .setPort(8080)
                .setRawPaths("b&uz", "q&ux")
                .appendRawPaths("f&oobar", "b&uzqux")
                .setRawQueryParameters(queryParameters)
                .setRawFragment("f&rag")
                .build();
        assertEquals(
                "http://h&ost.example.com:8080/b&uz/q&ux/f&oobar/b&uzqux?h&oge=f&uga&p&iyo=p&iyopiyo#f&rag",
                got.toString());

        queryParameters = new HashMap<>();
        queryParameters.put("p&iyo", "p&iyopiyo");

        got = new URIBuilderTiny()
                .setScheme("http")
                .setRawHost("h&ost.example.com")
                .setPort(8080)
                .setRawPathsByString("/b&uz/q&ux")
                .appendRawPathsByString("/f&oobar/b&uzqux")
                .addRawQueryParameter("h&oge", "f&uga")
                .addRawQueryParameters(queryParameters)
                .setRawFragment("f&rag")
                .build();
        assertEquals(
                "http://h&ost.example.com:8080/b&uz/q&ux/f&oobar/b&uzqux?h&oge=f&uga&p&iyo=p&iyopiyo#f&rag",
                got.toString());
    }

    @Test
    public void allowConsecutiveSlashAtQueryParameter() throws Exception {
        URI got = new URIBuilderTiny()
                .setScheme("http")
                .setHost("example.com")
                .setQueryParameter("url", "https://example.com")
                .build();
        assertEquals("http://example.com?url=https%3A%2F%2Fexample.com", got.toString());
    }

    private static class Foo {
        private String foo;

        public Foo(String foo) {
            this.foo = foo;
        }

        @Override
        public String toString() {
            return foo;
        }
    }
}
