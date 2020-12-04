package ca.dal.bartertrader.data.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.data.model.FirestorePostModel;
import ca.dal.bartertrader.data.model.Operation;
import ca.dal.bartertrader.domain.model.OfferModel;
import ca.dal.bartertrader.domain.model.OfferStatus;
import ca.dal.bartertrader.domain.model.PostModel;

public class OfferListLiveData extends LiveData<Operation> implements  EventListener<QuerySnapshot> {
    private final String TAG = "OfferListLiveData";

    private Query query;
    private ListenerRegistration listenerRegistration;
    private String currentUserId;


    OfferListLiveData(Query query, String currentUserId) {
        this.query = query;
        this.currentUserId = currentUserId;
    }

    @Override
    protected void onActive() {
        if(listenerRegistration == null) {
            listenerRegistration = query.addSnapshotListener(this);
        }
    }

    @Override
    protected void onInactive() {
        listenerRegistration.remove();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            return;
        }

        for (DocumentChange documentChange : querySnapshot.getDocumentChanges()) {
            switch (documentChange.getType()) {
                case ADDED:
                    setOfferOperation(documentChange, R.string.added);
                    break;

                case MODIFIED:
                    setOfferOperation(documentChange, R.string.modified);
                    break;
            }
        }
    }

    public void setOfferOperation(DocumentChange documentChange, Integer changeType) {
        String offerId = documentChange.getDocument().getId();
        String status = (String) documentChange.getDocument().get("status");

        DocumentReference providerRef = (DocumentReference) documentChange.getDocument().get("providerPost");
        DocumentReference receiverRef = (DocumentReference) documentChange.getDocument().get("receiverPost");

        Tasks.whenAllSuccess(providerRef.get(), receiverRef.get()).addOnSuccessListener(objects -> {
            DocumentSnapshot providerDoc = (DocumentSnapshot)objects.get(0);
            DocumentSnapshot receiverDoc = (DocumentSnapshot)objects.get(1);

            if(providerDoc.get("authUid").equals(currentUserId)){
                OfferModel addedProduct = new OfferModel(
                        offerId,
                        providerDoc.toObject(FirestorePostModel.class),
                        receiverDoc.toObject(FirestorePostModel.class),
                        OfferStatus.valueOf(status)
                );

                Operation operation = new Operation(addedProduct, changeType);
                setValue(operation);
            }
        });
    }

}
