package net.moznion.uribuildertiny;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.junit.Test;

public class URLEncoderTest {
    @Test(expected = RuntimeException.class)
    public void shouldRaiseExceptionWhenInvalidEncodingIsGiven() {
        new URLEncoder(new ConcreteEntityURLEncoder(new InvalidCharset("invalid", new String[] { "inv" })))
                .encode("foo");
    }

    private static class InvalidCharset extends Charset {
        /**
         * Initializes a new charset with the given canonical name and alias
         * set.
         *
         * @param canonicalName The canonical name of this charset
         * @param aliases An array of this charset's aliases, or null if it has no aliases
         */
        protected InvalidCharset(String canonicalName, String[] aliases) {
            super(canonicalName, aliases);
        }

        @Override
        public boolean contains(Charset cs) {
            return false;
        }

        @Override
        public CharsetDecoder newDecoder() {
            return null;
        }

        @Override
        public CharsetEncoder newEncoder() {
            return null;
        }
    }
}
