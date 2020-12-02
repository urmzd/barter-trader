package ca.dal.bartertrader.presentation.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.domain.model.PostModel;


public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<PostModel> postModelList;
    private ArrayList<PostModel> postModelListFull;
    private boolean isSort = true;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    FirebaseStorage firebaseStorage;

    public PostListAdapter(Context context, ArrayList<PostModel> postModel) {
        this.context = context;
        this.postModelList = postModel;
        postModelListFull = new ArrayList<>();
        postModelListFull.addAll(postModelList);
        firebaseStorage = FirebaseStorage.getInstance();
    }

    Comparator<PostModel> comparator = new Comparator<PostModel>() {
        @Override
        public int compare(PostModel movie, PostModel t1) {
            return movie.getTimestamp().toDate().compareTo(t1.getTimestamp().toDate());
        }
    };

    public void sortList() {
        if(isSort) {
            isSort = false;
            Collections.sort(postModelList, comparator);
        } else {
            isSort = true;
            Collections.sort(postModelList, Collections.reverseOrder(comparator));
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.provider_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PostModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(postModelListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PostModel item : postModelListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            postModelList.clear();
            postModelList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView card_image;
        TextView card_title, card_date, card_description;
        public ViewHolder(View view) {
            super(view);
            card_image = view.findViewById(R.id.card_image);
            card_title = view.findViewById(R.id.card_title);
            card_date = view.findViewById(R.id.card_date);
            card_description = view.findViewById(R.id.card_description);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        PostModel postModel = postModelList.get(position);

        viewHolder.card_title.setText(postModel.getTitle());
        viewHolder.card_date.setText(sdf.format(postModel.getTimestamp().toDate()));
        viewHolder.card_description.setText(postModel.getDescription());

        StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://barter-trader-6ca98.appspot.com/posts/"+postModel.getImageName()+".jpg");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(viewHolder.card_image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }
}

