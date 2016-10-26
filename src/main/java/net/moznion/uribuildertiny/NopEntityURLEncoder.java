package net.moznion.uribuildertiny;

import lombok.NonNull;

class NopEntityURLEncoder implements EntityURLEncoder {
    @Override
    public String encode(@NonNull Object input) {
        return input.toString();
    }
}
