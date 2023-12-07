package net.moznion.uribuildertiny;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;

class URLEncoder {
    private final EntityURLEncoder entityURLEncoder;

    URLEncoder(final EntityURLEncoder entityURLEncoder) {
        this.entityURLEncoder = entityURLEncoder;
    }

    public String encode(@NonNull Object input) {
        return entityURLEncoder.encode(input);
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
