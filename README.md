uribuilder-tiny ![Check](https://github.com/moznion/uribuilder-tiny/workflows/Check/badge.svg) [![Coverage Status](https://coveralls.io/repos/moznion/uribuilder-tiny/badge.svg?branch=master)](https://coveralls.io/r/moznion/uribuilder-tiny?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.moznion/uribuilder-tiny/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.moznion/uribuilder-tiny) [![javadoc.io](https://javadocio-badges.herokuapp.com/net.moznion/uribuilder-tiny/badge.svg)](https://javadocio-badges.herokuapp.com/net.moznion/uribuilder-tiny)
=============

Minimal and smart URI builder for Java.

Synopsis
---

### Manually use

```java
Map<String, String> queryParameters = new HashMap<>();
queryParameters.put("hoge", "fuga");

new URIBuilderTiny()
    .setScheme("https")
    .setHost("java.example.com")
    .setPort(8080)
    .setPaths("foo", "bar")
    .appendPaths("buz", "qux")
    .setQueryParameters(queryParameters)
    .addQueryParameter("piyo", "hogera")
    .setFragment("frag")
    .build(); // => same as `new URI(https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag)`
```

### With URI string as initial value

```java
new URIBuilderTiny("https://java.example.com/foo/bar")
    .setPort(8080)
    .appendPaths("buz", "qux")
    .addQueryParameter("hoge", "fuga")
    .addQueryParameter("piyo", "hogera")
    .setFragment("frag")
    .build(); // => same as `new URI(https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag)`
```

### With URI instance as initial value

```java
new URIBuilderTiny(new URI("https://java.example.com/foo/bar"))
    .setPort(8080)
    .appendPathsByString("/buz/qux")
    .addQueryParameter("hoge", "fuga")
    .addQueryParameter("piyo", "hogera")
    .setFragment("frag")
    .build(); // => same as `new URI(https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag)`
```

### Raw mode (not apply URL encode)

```java
new URIBuilderTiny()
    .setScheme("http")
    .setRawHost("h&ost.example.com")
    .setPort(8080)
    .setRawPaths(Arrays.asList("b&uz", "q&ux"))
    .appendRawPaths(Arrays.asList("f&oobar", "b&uzqux"))
    .setRawQueryParameter("h&oge", "f&uga")
    .addRawQueryParameter("p&iyo", "p&iyopiyo")
    .setRawFragment("f&rag")
    .build(); // => same as `http://h&ost.example.com:8080/b&uz/q&ux/f&oobar/b&uzqux?h&oge=f&uga&p&iyo=p&iyopiyo#f&rag`
```

Description
--

uribuilder-tiny is a minimal URI builder for Java.

[URIBuilder](https://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/client/utils/URIBuilder.html)
of [Apache HttpClient](https://hc.apache.org/httpcomponents-client-ga/) is a similar library.
It is a major and useful library. But it has lots of methods that are not much needed,
lacked some convenient methods (e.g. `appendPaths`) and it is a component of HttpClient so we cannot use it independently.

So I implement uribuilder-tiny to solve these frustration.
This library is minimal and independent from any others.

Methods
==

Please see javadoc.

[![javadoc.io](https://javadocio-badges.herokuapp.com/net.moznion/uribuilder-tiny/badge.svg)](https://javadocio-badges.herokuapp.com/net.moznion/uribuilder-tiny)

You can put any type instance as arguments for `URIBuilderTiny#setPaths()` and `URIBuilderTiny#appendPaths()`.

And you can also put any type instance as arguments of value of query parameters for
`URIBuilderTiny#setQueryParameters()`, `URIBuilderTiny#setQueryParameter()`,
`URIBuilderTiny#addQueryParameters()` and `URIBuilderTiny#addQueryParameter()`.

It will call `Object#toString()` for each these instances implicitly to stringify them.

Requires
==

- Java 7 or later

Author
--

moznion (<moznion@gmail.com>)

License
--

```
The MIT License (MIT)
Copyright © 2015 moznion, http://moznion.net/ <moznion@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

