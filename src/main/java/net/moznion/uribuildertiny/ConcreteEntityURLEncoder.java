package net.moznion.uribuildertiny;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import lombok.NonNull;

class ConcreteEntityURLEncoder implements EntityURLEncoder {
    private final String encodingCharsetName;

    ConcreteEntityURLEncoder(final Charset encodingCharset) {
        encodingCharsetName = encodingCharset.name();
    }

    @Override
    public String encode(@NonNull Object input) {
        try {
            return java.net.URLEncoder.encode(input.toString(), encodingCharsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
