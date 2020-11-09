package ca.dal.bartertrader.utils.handler.live_data.event;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class LiveEvent<OutputT> extends MutableLiveData<OutputT> {
    private final AtomicBoolean handled = new AtomicBoolean(false);

    @MainThread
    public void observe(@NotNull LifecycleOwner owner, @NotNull final Observer<? super OutputT> observer) {

        super.observe(owner, output -> {
            if (handled.compareAndSet(true, false)) {
                observer.onChanged(output);
            }
        });

    }

    @MainThread
    public void setValue(@Nullable OutputT value) {
        handled.set(true);
        super.setValue(value);
    }

    @MainThread
    public void call() {
        setValue(null);
    }
}
