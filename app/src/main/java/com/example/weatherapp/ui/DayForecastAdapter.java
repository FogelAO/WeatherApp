package com.example.weatherapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.entity.Forecast;
import com.example.weatherapp.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public final class DayForecastAdapter extends RecyclerView.Adapter<DayForecastAdapter.ForecastViewHolder> {
    @NonNull
    private List<Forecast> list = new ArrayList<>();

    void setList(@NonNull final List<Forecast> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_forecast, parent, false);
        return new ForecastViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final ForecastViewHolder holder, final int position) {
        final Forecast forecast = list.get(position);
        holder.bind(forecast);
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder {
        private final TextView temperatureTextView;
        private final TextView dateTextView;
        private final ImageView imageView;

        ForecastViewHolder(@NonNull final View itemView) {
            super(itemView);
            temperatureTextView = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.imageView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        void bind(@NonNull final Forecast data) {
            temperatureTextView.setText(itemView.getContext().getString(R.string.temperature_value, data.temperatureValue));
            dateTextView.setText(data.shortDate);
            imageView.setImageBitmap(ImageLoader.loadImage(data.imageUrl));
        }
    }
}
