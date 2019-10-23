package androidx.lifecycle;

import androidx.annotation.NonNull;

public interface ReferenceFunction<S, T> {
    void apply(@NonNull S input, @NonNull T output);
}
