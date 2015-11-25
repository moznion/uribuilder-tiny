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

    public String encode(@NonNull Object input) {
        try {
            return java.net.URLEncoder.encode(input.toString(), encodingCharsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> encode(@NonNull List<?> input) {
        final ArrayList<String> encodedList = new ArrayList<>();
        for (Object item : input) {
            encodedList.add(encode(item.toString()));
        }
        return encodedList;
    }

    public Map<String, String> encode(@NonNull Map<String, ?> input) {
        final HashMap<String, String> encodedMap = new HashMap<>();
        for (Map.Entry<String, ?> kv : input.entrySet()) {
            encodedMap.put(encode(kv.getKey()), encode(kv.getValue()));
        }
        return encodedMap;
    }
}
