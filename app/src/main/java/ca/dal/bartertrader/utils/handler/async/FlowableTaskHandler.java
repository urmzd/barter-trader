package ca.dal.bartertrader.utils.handler.async;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class FlowableTaskHandler<ResourceT> implements OnSuccessListener<ResourceT>, OnFailureListener {
    @Override
    public void onFailure(@NonNull Exception e) {

    }

    @Override
    public void onSuccess(ResourceT resourceT) {

    }
}
