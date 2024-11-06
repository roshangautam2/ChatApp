package com.example.chatapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.repository.ChatRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {

    private final ChatRepository chatRepository;

    public ChatViewModel(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public LiveData<List<ChatMessage>> getAllMessages() {
        return chatRepository.getAllMessages();
    }

    public void insertMessage(ChatMessage chatMessage) {
        chatRepository.insertMessage(chatMessage);
    }
}
