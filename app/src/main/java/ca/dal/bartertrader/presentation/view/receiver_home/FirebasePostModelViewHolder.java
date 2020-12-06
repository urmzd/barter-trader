package ca.dal.bartertrader.presentation.view.receiver_home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.data.model.FirebasePostModel;

class FirebasePostModelViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;
    private final TextView title;
    private final TextView desc;
    private final TextView date;
    private FirebasePostModel firebasePostModel;

    public FirebasePostModelViewHolder(@NonNull View itemView) {
        super(itemView);

        this.image = itemView.findViewById(R.id.item_image);
        this.title = itemView.findViewById(R.id.item_title);
        this.desc = itemView.findViewById(R.id.item_desc);
        this.date = itemView.findViewById(R.id.item_date);
    }

    public void bind(FirebasePostModel firebasePostModel) {
        this.firebasePostModel = firebasePostModel;
        Glide.with(image.getContext()).load(firebasePostModel.getImage()).into(image);
        title.setText(firebasePostModel.getTitle());
        desc.setText(firebasePostModel.getDescription());
        date.setText(firebasePostModel.getTimestamp().toDate().toString());
    }
}
