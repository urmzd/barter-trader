package ca.dal.bartertrader.utils.handler.resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<OutputT> {

    @NonNull
    private final Status status;

    @Nullable
    private final OutputT data;

    @Nullable
    private final Throwable error;

    private Resource(@NonNull Status status, @Nullable OutputT data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <OutputT> Resource<OutputT> fulfilled(@Nullable OutputT data) {
        return new Resource<>(Status.FULFILLED, data, null);
    }

    public static <OutputT> Resource<OutputT> rejected(@NonNull Throwable error) {
        return new Resource<>(Status.REJECTED, null, error);
    }

    public static <OutputT> Resource<OutputT> pending(@Nullable OutputT data) {
        return new Resource<>(Status.PENDING, data, null);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public OutputT getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }
}

