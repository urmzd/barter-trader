package ca.dal.bartertrader.utils.handler.async;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import io.reactivex.rxjava3.core.CompletableEmitter;

public class CompletableTaskHandler<T> implements OnCompleteListener<T> {

    private final CompletableEmitter emitter;
    private static Task<Object> task;

    private CompletableTaskHandler(CompletableEmitter emitter) {
        this.emitter = emitter;
    }

    public static <T> void assign(CompletableEmitter emitter, Task<T> task) {
        CompletableTaskHandler<T> handler = new CompletableTaskHandler<>(emitter);
        task.addOnCompleteListener(handler);
    }

    @Override
    public void onComplete(@NonNull Task task) {
        if (task.isSuccessful()) {
            emitter.onComplete();
        } else {
            if (!emitter.isDisposed()) {
                emitter.onError(task.getException());
            }
        }
    }
}
