package com.alc.macharika_ryan_s2023365.adapter;

// Ryan Macharika - S2023365

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alc.macharika_ryan_s2023365.model.CurrencyItem;
import com.alc.macharika_ryan_s2023365.R;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private List<CurrencyItem> currencyItems;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(CurrencyItem currencyItem);
    }

    public CurrencyAdapter(List<CurrencyItem> currencyItems, Context context) {
        this.currencyItems = currencyItems;
        this.context = context;
    }

    public void updateData(List<CurrencyItem> newCurrencyItems) {
        currencyItems.clear();
        currencyItems.addAll(newCurrencyItems);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_item, parent, false);

        return new CurrencyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        CurrencyItem currency = currencyItems.get(position);
        holder.currencyNameTextView.setText(currency.getCurrencyCode());
        holder.conversionRateText.setText(String.valueOf(currency.getConversionRate()));

        if (currency.getConversionRate() < 5) {
            holder.conversionRateText.setTextColor(context.getResources().getColor(R.color.teal_700));
        } else if (currency.getConversionRate() > 5) {
            holder.conversionRateText.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            holder.conversionRateText.setTextColor(context.getResources().getColor(R.color.black));
        }

        String flagClassName = currency.getCountryCode().toLowerCase();
        int resourceId = context.getResources().getIdentifier(flagClassName, "drawable", context.getPackageName());

        if (resourceId != 0) {
            holder.flagImageView.setImageResource(resourceId);
        }
    }

    @Override
    public int getItemCount() {
        return currencyItems.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder {
        ImageView flagImageView;
        TextView currencyNameTextView;
        TextView conversionRateText;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            flagImageView = itemView.findViewById(R.id.flagImageView);
            currencyNameTextView = itemView.findViewById(R.id.currencyNameTextView);
            conversionRateText = itemView.findViewById(R.id.conversionRateText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = CurrencyViewHolder.this.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            System.out.println("Clicked on " + currencyItems.get(position).getCurrencyCode());
                            onItemClickListener.onItemClick(currencyItems.get(position));
                        }
                    }
                }
            });
        }
    }
}


