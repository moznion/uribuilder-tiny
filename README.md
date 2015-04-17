tiny-uribuilder
=============

Minimal URI builder.

Synopsis
---

### Manually use

```java
Map<String, String> queryParameters = new HashMap<>();
queryParameters.put("hoge", "fuga");

new TinyURIBuilder()
	.setSchema("https")
	.setHost("java.example.com")
	.setPort(8080)
	.setPaths(Arrays.asList("foo", "bar"))
	.appendPaths(Arrays.asList("buz", "qux"))
	.setQueryParameters(queryParameters)
	.addQueryParameter("piyo", "hogera")
	.setFragment("frag")
	.build(); // => same as `new URI(https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag)`
```

### With URI string as initial value

```java
new TinyURIBuilder("https://java.example.com/foo/bar")
	.setPort(8080)
	.appendPaths("/buz/qux")
	.addQueryParameter("hoge", "fuga")
	.addQueryParameter("piyo", "hogera")
	.setFragment("frag")
	.build(); // => same as `new URI(https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag)`
```

### With URI instance as initial value

```java
new TinyURIBuilder(new URI("https://java.example.com/foo/bar"))
	.setPort(8080)
	.appendPaths("/buz/qux")
	.addQueryParameter("hoge", "fuga")
	.addQueryParameter("piyo", "hogera")
	.setFragment("frag")
	.build(); // => same as `new URI(https://java.example.com:8080/foo/bar/buz/qux?hoge=fuga&piyo=hogera#frag)`
```

Description
--

TBD

Author
--

moznion (<moznion@gmail.com>)

License
--

```
The MIT License (MIT)
Copyright © 2014 moznion, http://moznion.net/ <moznion@gmail.com>

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

