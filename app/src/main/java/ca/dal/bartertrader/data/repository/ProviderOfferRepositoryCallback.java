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
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference productsRef;
    private Query query;


    public ProviderOfferRepositoryCallback(FirebaseFirestore firebaseFirestore, FirebaseAuthDataSource firebaseAuthDataSource) {
        this.firebaseAuthDataSource = firebaseAuthDataSource;
        this.firebaseFirestore = firebaseFirestore;
        this.productsRef = this.firebaseFirestore.collection("offers");
    }


    @Override
    public OfferListLiveData getProductListLiveData() {
        query = productsRef.orderBy("receiverPost");
        return new OfferListLiveData(query, firebaseAuthDataSource.getUser().getUid());
    }

    public void setStatus(OfferModel offer, String status) {
        productsRef.document(offer.getId()).update("status", status);
        if (status.equals("ACCEPTED")) {
            productsRef.document(offer.getId()).addSnapshotListener((value, error) -> {
                if (error == null) {
                    DocumentReference receiverPost = (DocumentReference) value.get("receiverPost");

                    receiverPost.update("providerId", offer.getProviderPost().getAuthUid());

                }
            });
        }
    }
}

