package net.moznion.uribuildertiny;

import lombok.NonNull;

interface EntityURLEncoder {
  String encode(@NonNull Object input);
}
