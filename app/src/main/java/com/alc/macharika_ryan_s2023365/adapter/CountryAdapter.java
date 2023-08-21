package com.alc.macharika_ryan_s2023365.adapter;

// Ryan Macharika - S2023365

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alc.macharika_ryan_s2023365.model.Country;
import com.alc.macharika_ryan_s2023365.R;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private List<Country> originalList;
    private List<Country> filteredList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Country country);
    }

    public CountryAdapter(List<Country> itemList, OnItemClickListener itemClickListener) {
        this.originalList = itemList;
        this.filteredList = new ArrayList<>(itemList);
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = filteredList.get(position);
        holder.bind(country);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (Country item : originalList) {
                if (item.getName().toLowerCase().contains(query.toLowerCase()) || item.getCurrencyCode().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView countryNameTextView;
        private TextView currencyCodeTextView;
        private ImageView flagImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryNameTextView = itemView.findViewById(R.id.countryNameTextView);
            currencyCodeTextView = itemView.findViewById(R.id.currencyCodeTextView);
            flagImageView = itemView.findViewById(R.id.flagImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Country clickedCountry = filteredList.get(position);
                        itemClickListener.onItemClick(clickedCountry);
                    }
                }
            });
        }

        public void bind(Country country) {
            countryNameTextView.setText(country.getName());
            currencyCodeTextView.setText(country.getCurrencyCode());
            String flagClassName = country.getCountryCode().toLowerCase();
            int resourceId = itemView.getContext().getResources().getIdentifier(flagClassName, "drawable", itemView.getContext().getPackageName());
            // Set the resource to your flagImageView
            flagImageView.setImageResource(resourceId);
        }
    }
}


