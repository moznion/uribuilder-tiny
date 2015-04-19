package net.moznion.tinyuribuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TinyURIBuilderTest {
  @Test
  public void shouldGetURIInstanceByManual() throws URISyntaxException {
    Map<String, String> queryParameters = new HashMap<>();
    queryParameters.put("hoge", "fuga");

    URI got = new TinyURIBuilder()
        .setSchema("https")
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
    URI got = new TinyURIBuilder("https://java.example.com/foo/bar")
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
    URI got = new TinyURIBuilder(new URI("https://java.example.com/foo/bar"))
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

    URI got = new TinyURIBuilder(new URI(""))
        .setSchema("https")
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
    URI got = new TinyURIBuilder()
        .setSchema("http")
        .setHost("java.example.com")
        .setPort(8080)
        .setPaths(Arrays.asList("foo", "bar"))
        .appendPaths(Arrays.asList("buz", "qux"))
        .build();
    assertEquals("http://java.example.com:8080/foo/bar/buz/qux", got.toString());
  }

  @Test
  public void testEmptyPort() throws URISyntaxException {
    URI got = new TinyURIBuilder()
        .setSchema("http")
        .setHost("java.example.com")
        .build();
    assertEquals("http://java.example.com", got.toString());
  }

  @Test
  public void testSetAndAddQueryParamWithStringPair() throws URISyntaxException {
    URI got = new TinyURIBuilder()
        .setSchema("http")
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

    URI got = new TinyURIBuilder()
        .setSchema("http")
        .setHost("java.example.com")
        .setQueryParameters(initQueryParameters)
        .addQueryParameters(queryParameters)
        .build();
    assertEquals("http://java.example.com?hoge=fuga&piyo=piyopiyo", got.toString());
  }

  @Test
  public void testForTrailingSlash() throws URISyntaxException {
    URI got = new TinyURIBuilder()
        .setSchema("http")
        .setHost("java.example.com/")
        .build();
    assertEquals("http://java.example.com/", got.toString());
  }
}
