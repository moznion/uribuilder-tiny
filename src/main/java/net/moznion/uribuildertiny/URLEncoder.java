package net.moznion.uribuildertiny;

import lombok.NonNull;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class URLEncoder {
    private final String encodingCharsetName;

    public URLEncoder(Charset encodingCharset) {
        this.encodingCharsetName = encodingCharset.name();
    }

    public <T> String encode(@NonNull T input) {
        try {
            return java.net.URLEncoder.encode(input.toString(), encodingCharsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<String> encode(@NonNull List<T> input) {
        final ArrayList<String> encodedList = new ArrayList<>();
        for (T item : input) {
            encodedList.add(encode(item.toString()));
        }
        return encodedList;
    }

    public <T> Map<String, String> encode(@NonNull Map<String, T> input) {
        final HashMap<String, String> encodedMap = new HashMap<>();
        for (Map.Entry<String, T> kv : input.entrySet()) {
            encodedMap.put(encode(kv.getKey()), encode(kv.getValue()));
        }
        return encodedMap;
    }
}
