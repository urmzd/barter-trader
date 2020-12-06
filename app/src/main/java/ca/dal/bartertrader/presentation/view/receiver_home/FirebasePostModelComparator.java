package ca.dal.bartertrader.presentation.view.receiver_home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import ca.dal.bartertrader.data.model.FirebasePostModel;

class FirebasePostModelComparator extends DiffUtil.ItemCallback<FirebasePostModel> {

    @Override
    public boolean areItemsTheSame(@NonNull FirebasePostModel oldItem, @NonNull FirebasePostModel newItem) {
        if (oldItem.getProviderId() == null) {
            return false;
        }
        return oldItem.getProviderId().equals(newItem.getProviderId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull FirebasePostModel oldItem, @NonNull FirebasePostModel newItem) {
        return oldItem.getTimestamp().equals(newItem.getTimestamp());
    }
}
