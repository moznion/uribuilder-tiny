package net.moznion.uribuildertiny;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class URLEncoder {
    private final String encodingCharsetName;

    public URLEncoder(Charset encodingCharset) {
        this.encodingCharsetName = encodingCharset.name();
    }

    public <T> String encode(T input) {
        try {
            return java.net.URLEncoder.encode(input.toString(), encodingCharsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<String> encode(List<T> input) {
        return input.stream()
                .map(item -> encode(item.toString()))
                .collect(Collectors.toList());
    }

    public <T> Map<String, String> encode(Map<String, T> input) {
        return input.entrySet().stream()
                .collect(Collectors.toMap(
                        kv -> encode(kv.getKey()),
                        kv -> encode(kv.getValue())));
    }
}
