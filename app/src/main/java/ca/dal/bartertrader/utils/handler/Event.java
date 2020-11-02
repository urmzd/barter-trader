package ca.dal.bartertrader.utils.handler;

import androidx.annotation.Nullable;

public class Event<T>{
    private Boolean handled = false;
    private final T payload;

    public Event(T payload) {
        this.payload = payload;
    }

    // Only for testing purposes.
    public Boolean getHandled() {
        return this.handled;
    }

    public T handle() {
        this.handled = true;
        return payload;
    }

    @Nullable
    public T peek() {
        if (this.handled) {
            return payload;
        }

        return null;
    }
}
