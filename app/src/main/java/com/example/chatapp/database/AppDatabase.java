package com.example.chatapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.chatapp.dao.ChatMessageDao;
import com.example.chatapp.model.ChatMessage;

@Database(entities = {ChatMessage.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ChatMessageDao chatMessageDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "message_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
