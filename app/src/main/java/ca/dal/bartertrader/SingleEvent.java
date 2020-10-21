package ca.dal.bartertrader;

public class SingleEvent<T> {
    private boolean handled;
    private final T data;

    public SingleEvent(T data) {
        this.handled = false;
        this.data = data;
    }

    public T peek() {
        return this.data;
    }

    public T handle() {
        if (handled) {
            return null;
        }
        handled = true;
        return data;
    }
}
