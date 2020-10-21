package ca.dal.bartertrader;

import android.util.Pair;

public class Event<T, E> {
    private Boolean handled = false;
    private final T action;
    private final E payload;

    public Event(T action, E payload) {
        this.action = action;
        this.payload = payload;
    }

    public Pair<T, E> handle() {
        this.handled = true;
        return new Pair<T, E>(action, payload);
    }

    public Pair<T, E> peek() {
        if (this.handled) {
            return new Pair<T, E>(action, payload);
        }

        return null;
    }
}
