package com.example.chatapp.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;

public class ImageSelectionAdapter extends RecyclerView.Adapter<ImageSelectionAdapter.ViewHolder> {

    private final int[] imageOptions;
    private final OnImageClickListener onImageClickListener;

    public ImageSelectionAdapter(int[] imageOptions, OnImageClickListener onImageClickListener) {
        this.imageOptions = imageOptions;
        this.onImageClickListener = onImageClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imageResId = imageOptions[position];
        holder.imageView.setImageResource(imageResId);
        holder.itemView.setOnClickListener(v -> onImageClickListener.onImageClick(imageResId));
    }

    @Override
    public int getItemCount() {
        return imageOptions.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSelected);
        }
    }

    public interface OnImageClickListener {
        void onImageClick(int imageResId);
    }
}
