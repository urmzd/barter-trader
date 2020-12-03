package ca.dal.bartertrader.presentation.view.profile;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import ca.dal.bartertrader.R;

public class ReviewAdapter extends FirestoreRecyclerAdapter<Review, ReviewAdapter.ReviewHolder> {
    private String uid;

    public ReviewAdapter (@NonNull FirestoreRecyclerOptions<Review> options, String uid) {
        super(options);
        this.uid = uid;
    }

    @Override
    protected void onBindViewHolder(@NonNull ReviewHolder holder, int position, @NonNull Review model) {
        holder.displayName.setText(model.getFrom());
        holder.review.setText(model.getMessage());
        holder.date.setText(model.getTimestamp().toString());
        holder.rating.setRating(model.getRating());
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, parent, false);
        return new ReviewHolder(v);
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        TextView displayName, review, date;
        RatingBar rating;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            displayName = itemView.findViewById(R.id.name);
            review = itemView.findViewById(R.id.review);
            date = itemView.findViewById(R.id.datePosted);
            rating = itemView.findViewById(R.id.ratingBar);
        }
    }
}
