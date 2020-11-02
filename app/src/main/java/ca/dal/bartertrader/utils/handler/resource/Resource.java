package ca.dal.bartertrader.utils.handler.resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    private final Status status;

    @Nullable
    private final T data;

    @Nullable
    private final Throwable error;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> fulfilled(@NonNull T data) {
        return new Resource<>(Status.FULFILLED, data, null);
    }

    public static <T> Resource<T> rejected(@NonNull Throwable error) {
        return new Resource<>(Status.REJECTED, null, error);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }
}

