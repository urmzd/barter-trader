package ca.dal.bartertrader.data.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.domain.model.OfferModel;
import ca.dal.bartertrader.presentation.view_model.ProviderOfferViewModel;

public class ProviderOfferRepositoryCallback implements ProviderOfferViewModel.OfferRepository {
    private FirebaseAuthDataSource firebaseAuthDataSource;
    private CollectionReference offersRef;


    public ProviderOfferRepositoryCallback(FirebaseFirestore firebaseFirestore, FirebaseAuthDataSource firebaseAuthDataSource) {
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.offersRef = firebaseFirestore.collection("offers");
    }


    @Override
    public OfferListLiveData getOfferListLiveData() {
        Query query = offersRef.whereEqualTo("providerId", firebaseAuthDataSource.getUser().getUid());
        return new OfferListLiveData(query);
    }

    public void setStatus(OfferModel offer, String status) {
        offersRef.document(offer.getId()).update("status", status);
        if (status.equals("ACCEPTED")) {
            offersRef.document(offer.getId()).addSnapshotListener((value, error) -> {
                if (error == null) {
                    DocumentReference receiverPost = (DocumentReference) value.get("receiverPost");

                    receiverPost.update("providerId", offer.getProviderPost().getAuthUid());

                }
            });
        }
    }
}

