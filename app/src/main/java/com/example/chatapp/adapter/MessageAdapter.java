package com.example.chatapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatapp.R;
import com.example.chatapp.model.ChatMessage;
import com.squareup.picasso.Picasso;  // Using Picasso to load images

public class MessageAdapter extends ListAdapter<ChatMessage, MessageAdapter.MessageViewHolder> {

    public MessageAdapter() {
        super(new DiffUtil.ItemCallback<ChatMessage>() {
            @Override
            public boolean areItemsTheSame(ChatMessage oldItem, ChatMessage newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(ChatMessage oldItem, ChatMessage newItem) {
                return oldItem.getImageUrl().equals(newItem.getImageUrl());
            }
        });
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        ChatMessage message = getItem(position);
        holder.senderTextView.setText(message.getSenderName());
        Picasso.get().load(message.getImageUrl()).into(holder.messageImageView); // Load the image
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView senderTextView;
        public ImageView messageImageView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderNameTextView);
            messageImageView = itemView.findViewById(R.id.chatImageView);
        }
    }
}
