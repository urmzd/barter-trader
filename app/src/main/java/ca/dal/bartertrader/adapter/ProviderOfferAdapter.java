package ca.dal.bartertrader.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.dal.bartertrader.databinding.CardOfferBinding;
import ca.dal.bartertrader.domain.model.OfferModel;
import ca.dal.bartertrader.domain.model.OfferStatus;
import ca.dal.bartertrader.utils.listeners.OfferListener;

public class ProviderOfferAdapter extends RecyclerView.Adapter<ProviderOfferAdapter.OfferViewHolder> {
    private final String TAG = "ProviderOfferAdapter";

    private OfferListener offerListener;
    private ArrayList<OfferModel> offers;

    public ProviderOfferAdapter(OfferListener offerListener, ArrayList<OfferModel> offers) {
        this.offerListener = offerListener;
        this.offers = offers;
    }

    @NonNull
    @Override
    public ProviderOfferAdapter.OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardOfferBinding cardOfferBinding = CardOfferBinding.inflate(layoutInflater, parent, false);
        return new OfferViewHolder(cardOfferBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderOfferAdapter.OfferViewHolder holder, int position) {
        final OfferModel offer = offers.get(position);
        holder.cardOfferBinding.setOffer(offer);
        holder.cardOfferBinding.executePendingBindings();

        // Handle offer action visibility
        if (offer.getStatus().equals(OfferStatus.valueOf("ACCEPTED"))) {
            setAcceptedOfferUI(holder.cardOfferBinding);
        }

        if (offer.getStatus().equals(OfferStatus.valueOf("DECLINED"))) {
            setDeclinedOfferUI(holder.cardOfferBinding);
        }

        if (offer.getStatus().equals(OfferStatus.valueOf("COMPLETE"))) {
            setCompleteOfferUI(holder.cardOfferBinding);
        }

        if (offer.getStatus().equals(OfferStatus.valueOf("PENDING"))) {
            setPendingOfferUI(holder.cardOfferBinding);
        }

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    private void setPendingOfferUI(CardOfferBinding cardOfferBinding) {
        cardOfferBinding.acceptButton.setVisibility(View.VISIBLE);
        cardOfferBinding.declineButton.setVisibility(View.VISIBLE);
    }

    private void setAcceptedOfferUI(CardOfferBinding cardOfferBinding) {
        cardOfferBinding.acceptButton.setVisibility(View.GONE);
        cardOfferBinding.declineButton.setVisibility(View.GONE);

        cardOfferBinding.reviewButton.setVisibility(View.VISIBLE);
        cardOfferBinding.reviewButton.setEnabled(true);
        cardOfferBinding.acceptedText.setVisibility(View.VISIBLE);
    }

    private void setDeclinedOfferUI(CardOfferBinding cardOfferBinding) {
        cardOfferBinding.declineButton.setVisibility(View.GONE);
        cardOfferBinding.acceptButton.setVisibility(View.GONE);

        cardOfferBinding.declinedText.setVisibility(View.VISIBLE);
    }

    private void setCompleteOfferUI(CardOfferBinding cardOfferBinding) {
        cardOfferBinding.reviewButton.setVisibility(View.GONE);
        cardOfferBinding.completeText.setVisibility(View.VISIBLE);
    }

    class OfferViewHolder extends RecyclerView.ViewHolder {
        CardOfferBinding cardOfferBinding;

        public OfferViewHolder(@NonNull CardOfferBinding cardOfferBinding) {
            super(cardOfferBinding.getRoot());
            this.cardOfferBinding = cardOfferBinding;

            cardOfferBinding.acceptButton.setOnClickListener(v -> {
                Log.d("TAG", getOffer().getReceiverPost().getDescription());
                getOffer().setStatus(OfferStatus.valueOf("ACCEPTED"));
                offerListener.onAcceptDeclineClick(getOffer(), "ACCEPTED");
                notifyItemChanged(getAbsoluteAdapterPosition());
            });

            cardOfferBinding.declineButton.setOnClickListener(v -> {
                getOffer().setStatus(OfferStatus.valueOf("DECLINED"));
                offerListener.onAcceptDeclineClick(getOffer(), "DECLINED");
                notifyItemChanged(getAbsoluteAdapterPosition());
            });

            cardOfferBinding.reviewButton.setOnClickListener(v -> {
                offerListener.onReviewClick(getOffer());
                notifyItemChanged(getAbsoluteAdapterPosition());
            });

        }

        private OfferModel getOffer() {
            return offers.get(getAbsoluteAdapterPosition());
        }
    }


}
