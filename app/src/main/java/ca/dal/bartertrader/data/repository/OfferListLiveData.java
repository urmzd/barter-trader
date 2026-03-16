package ca.dal.bartertrader.data.repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import ca.dal.bartertrader.R;
import ca.dal.bartertrader.data.model.FirestorePostModel;
import ca.dal.bartertrader.data.model.Operation;
import ca.dal.bartertrader.domain.model.OfferModel;
import ca.dal.bartertrader.domain.model.OfferStatus;

public class OfferListLiveData extends LiveData<Operation> implements EventListener<QuerySnapshot> {
    private final Query query;
    private ListenerRegistration listenerRegistration;


    OfferListLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        if (listenerRegistration == null) {
            listenerRegistration = query.addSnapshotListener(this);
        }
    }

    @Override
    protected void onInactive() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null || querySnapshot == null) {
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

        if (providerRef == null || receiverRef == null) {
            return;
        }

        Tasks.whenAllSuccess(providerRef.get(), receiverRef.get()).addOnSuccessListener(objects -> {
            if (objects.size() < 2) {
                return;
            }
            DocumentSnapshot providerDoc = (DocumentSnapshot) objects.get(0);
            DocumentSnapshot receiverDoc = (DocumentSnapshot) objects.get(1);


            OfferStatus offerStatus;
            try {
                offerStatus = OfferStatus.valueOf(status);
            } catch (IllegalArgumentException | NullPointerException ex) {
                return;
            }

            OfferModel addedOffer = new OfferModel(
                    offerId,
                    providerDoc.toObject(FirestorePostModel.class),
                    receiverDoc.toObject(FirestorePostModel.class),
                    offerStatus
            );

            Operation operation = new Operation(addedOffer, changeType);
            setValue(operation);

        });
    }

}
