package ca.dal.bartertrader;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.PropertyName;

public class SwitchRolesViewModel extends ViewModel {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private MutableLiveData<FirebaseUser> authUser = new MutableLiveData<>();
    private MutableLiveData<FirestoreUser> firestoreUser = new MutableLiveData<>();

    private boolean switchRole = false;

    public SwitchRolesViewModel() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        authUser.setValue(firebaseAuth.getCurrentUser());
        setUser();
    }
    public void setUser() {
        firebaseFirestore.collection("users").document(authUser.getValue().getUid())
                .get().addOnCompleteListener(task -> {
                    firestoreUser.setValue(task.getResult().toObject(FirestoreUser.class));
                    if (switchRole) {
                        switchRoles();
                        switchRole = false;
                    }
                });
    }

    public void switchRoles() {
        if(firestoreUser.getValue() != null) {
            firestoreUser.setValue(new FirestoreUser(!firestoreUser.getValue().getProvider()));
            firebaseFirestore
                    .collection("users")
                    .document(authUser.getValue().getUid())
                    .set(firestoreUser.getValue());
        }else{
            switchRole = true;
        }

    }

    public LiveData<FirebaseUser> getAuthUser() {
        return authUser;
    }

    public LiveData<FirestoreUser> getFirestoreUser() {
        return firestoreUser;
    }

}

class FirestoreUser {
    @PropertyName("provider")
    public boolean provider;

    public FirestoreUser() {}

    public FirestoreUser(boolean provider) {
        this.provider = provider;
    }

    public boolean getProvider() {
        return provider;
    }

}
