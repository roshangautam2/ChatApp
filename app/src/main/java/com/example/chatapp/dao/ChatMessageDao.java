package com.example.chatapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.chatapp.model.ChatMessage;
import java.util.List;

@Dao
public interface ChatMessageDao {

    @Insert
    void insert(ChatMessage message);

    @Query("SELECT * FROM chat_messages")
    LiveData<List<ChatMessage>> getAllMessages();
}
