package ca.dal.bartertrader.presentation.view.receiver_home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import org.jetbrains.annotations.NotNull;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.data.model.FirebasePostModel;

public class ReceiverHomeAdapter extends PagingDataAdapter<FirebasePostModel, FirebasePostModelViewHolder> {


    public ReceiverHomeAdapter(@NotNull DiffUtil.ItemCallback<FirebasePostModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public FirebasePostModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_item, parent, false);

        return new FirebasePostModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FirebasePostModelViewHolder holder, int position) {
        FirebasePostModel post = getItem(position);

        holder.bind(post);
    }
}
