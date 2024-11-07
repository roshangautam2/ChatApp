package com.example.chatapp.dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.chatapp.model.ChatMessage;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatMessageDao_Impl implements ChatMessageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChatMessage> __insertionAdapterOfChatMessage;

  public ChatMessageDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChatMessage = new EntityInsertionAdapter<ChatMessage>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `chat_messages` (`id`,`senderName`,`imageUrl`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChatMessage value) {
        stmt.bindLong(1, value.getId());
        if (value.getSenderName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSenderName());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getImageUrl());
        }
      }
    };
  }

  @Override
  public void insert(final ChatMessage message) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfChatMessage.insert(message);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<ChatMessage>> getAllMessages() {
    final String _sql = "SELECT * FROM chat_messages";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"chat_messages"}, false, new Callable<List<ChatMessage>>() {
      @Override
      public List<ChatMessage> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSenderName = CursorUtil.getColumnIndexOrThrow(_cursor, "senderName");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final List<ChatMessage> _result = new ArrayList<ChatMessage>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ChatMessage _item;
            final String _tmpSenderName;
            if (_cursor.isNull(_cursorIndexOfSenderName)) {
              _tmpSenderName = null;
            } else {
              _tmpSenderName = _cursor.getString(_cursorIndexOfSenderName);
            }
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            _item = new ChatMessage(_tmpSenderName,_tmpImageUrl);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
