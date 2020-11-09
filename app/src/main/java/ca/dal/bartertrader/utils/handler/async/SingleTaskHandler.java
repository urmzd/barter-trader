package ca.dal.bartertrader.utils.handler.async;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SingleTaskHandler<T> implements OnSuccessListener<T>, OnFailureListener {

    private final SingleEmitter<? super T> emitter;

    private SingleTaskHandler(SingleEmitter<? super T> emitter) {
        this.emitter = emitter;
    }

    public static <T> void assign(SingleEmitter<? super T> emitter, Task<T> task) {
        SingleTaskHandler<T> handler = new SingleTaskHandler<>(emitter);
        task.addOnSuccessListener(handler);
        task.addOnFailureListener(handler);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        if (!emitter.isDisposed()) {
            emitter.onError(e);
        }
    }

    @Override
    public void onSuccess(T data) {
        emitter.onSuccess(data);
    }
}
