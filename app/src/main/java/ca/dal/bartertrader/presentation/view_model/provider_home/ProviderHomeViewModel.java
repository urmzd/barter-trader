package ca.dal.bartertrader.presentation.view_model.provider_home;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.dal.bartertrader.domain.model.PostModel;
import ca.dal.bartertrader.domain.use_case.posts.GetPostsUseCase;

public class ProviderHomeViewModel extends ViewModel {

    private final GetPostsUseCase getPostsUseCase;
    private ArrayList<PostModel> postModels = new ArrayList<>();
    public MutableLiveData<ArrayList<PostModel>> postItemList = new MutableLiveData<>();
    public ProviderHomeViewModel(GetPostsUseCase getPostsUseCase) {
        this.getPostsUseCase = getPostsUseCase;
        getPostsUseCase.executeMyPost().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        document.getId();
                        PostModel postModel = document.toObject(PostModel.class);
                        postModel.setImage(Uri.parse("https://firebasestorage.googleapis.com/v0/b/barter-trader-6ca98.appspot.com/o/posts%2F"+document.getId()+".jpg"));
                        postModel.setImageName(document.getId());
                        postModels.add(postModel/*document.toObject(PostModel.class)*/);
                    }
                    postItemList.setValue(postModels);
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
                //DialogProgress.hide(ChaptersActivity.this);
            }
        });
    }

}
