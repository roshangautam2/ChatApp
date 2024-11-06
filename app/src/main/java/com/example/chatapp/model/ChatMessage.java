package com.example.chatapp.model;

import android.widget.ImageView;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "chat_messages")
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String senderName;  // Sender of the message (e.g., "Sita", "Husband")
     private String imageUrl;    // URL for the image (if any)


    // Constructor for image messages
    public ChatMessage(String senderName, String imageUrl) {
        this.senderName = senderName;
         this.imageUrl = imageUrl; // URL of the image
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
