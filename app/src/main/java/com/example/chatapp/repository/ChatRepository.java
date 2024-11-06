package com.example.chatapp.repository;

import androidx.lifecycle.LiveData;

import com.example.chatapp.dao.ChatMessageDao;
import com.example.chatapp.model.ChatMessage;

import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRepository {

    private final ChatMessageDao chatMessageDao;
    private final DatabaseReference databaseReference;
    private final Executor executor = Executors.newSingleThreadExecutor(); // Executor for background tasks

    public ChatRepository(ChatMessageDao chatMessageDao, DatabaseReference databaseReference) {
        this.chatMessageDao = chatMessageDao;
        this.databaseReference = databaseReference;
    }

    public LiveData<List<ChatMessage>> getAllMessages() {
        return chatMessageDao.getAllMessages();
    }

    public void insertMessage(ChatMessage chatMessage) {
        // Run database insert operation on a background thread
        executor.execute(() -> {
            chatMessageDao.insert(chatMessage);
        });
    }
}
