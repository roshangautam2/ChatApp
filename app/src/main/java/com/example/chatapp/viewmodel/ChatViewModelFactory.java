package com.example.chatapp.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatapp.repository.ChatRepository;

public class ChatViewModelFactory implements ViewModelProvider.Factory {

    private final ChatRepository chatRepository;

    public ChatViewModelFactory(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChatViewModel.class)) {
            //noinspection unchecked
            return (T) new ChatViewModel(chatRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
