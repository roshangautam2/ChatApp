package com.example.chatapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.adapter.MessageAdapter;
import com.example.chatapp.database.AppDatabase;
import com.example.chatapp.model.ChatMessage;
import com.example.chatapp.repository.ChatRepository;
import com.example.chatapp.viewmodel.ChatViewModel;
import com.example.chatapp.viewmodel.ChatViewModelFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private ChatViewModel chatViewModel;
    private DatabaseReference databaseReference;
    private ImageView imageViewSelected;
    private int selectedImageResId = -1; // To keep track of selected image

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference("messages");
        AppDatabase db = AppDatabase.getDatabase(this);
        ChatRepository chatRepository = new ChatRepository(db.chatMessageDao(), databaseReference);
        ChatViewModelFactory factory = new ChatViewModelFactory(chatRepository);
        chatViewModel = new ViewModelProvider(this, factory).get(ChatViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);
        chatViewModel.getAllMessages().observe(this, messages -> {
            if (messages != null && !messages.isEmpty()) {
                adapter.submitList(messages);
                recyclerView.smoothScrollToPosition(messages.size() - 1); // Scroll to the latest message
            }
        });

        ImageView selectImageButton = findViewById(R.id.selectImageButton);
        Button sendButton = findViewById(R.id.sendButton);
        imageViewSelected = findViewById(R.id.imageViewSelected);
        selectImageButton.setOnClickListener(v -> showImageSelectionDialog());

        sendButton.setOnClickListener(v -> {
            if (selectedImageResId != -1) {
                String imageUrl = "android.resource://" + getPackageName() + "/" + selectedImageResId;
                ChatMessage message = new ChatMessage("Sita", imageUrl);
                String messageId = databaseReference.push().getKey();
                if (messageId != null) {
                    databaseReference.child(messageId).setValue(message);
                    chatViewModel.insertMessage(message);
                    imageViewSelected.setVisibility(View.GONE);
                    selectedImageResId = -1;
                    Toast.makeText(MainActivity.this, "Image Sent!", Toast.LENGTH_SHORT).show();
                    sendAutoResponse();
                }
            } else {
                Toast.makeText(MainActivity.this, "Please select an image first", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showImageSelectionDialog() {
        int[] imageResIds = {
                R.drawable.namaste_icon,
                R.drawable.surprise_icon,
                R.drawable.good_job_icon,
                R.drawable.angry_icon,
                R.drawable.goodbye_icon
        };
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_image_selection, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        LinearLayout imageContainer = dialogView.findViewById(R.id.imageContainer);
        for (int imageResId : imageResIds) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResId);
            imageView.setPadding(10, 10, 10, 10);
            imageView.setAdjustViewBounds(true);
            imageView.setMaxWidth(200);
            imageView.setMaxHeight(200);
            imageView.setOnClickListener(v -> {
                selectedImageResId = imageResId;
                imageViewSelected.setVisibility(View.VISIBLE);
                imageViewSelected.setImageResource(selectedImageResId); // Show selected image
                dialog.dismiss();
            });
            imageContainer.addView(imageView);
        }

        dialog.show();
    }
    private void sendAutoResponse() {
        String[] autoName = {
                "Husband", "Son", "Daughter"
        };
        int[] autoResponseResIds = {
                R.drawable.surprise_icon,
                R.drawable.good_job_icon,
                R.drawable.angry_icon,
                R.drawable.goodbye_icon
        };
        int randomIndex = (int) (Math.random() * autoResponseResIds.length);
        String autoResponseUrl = "android.resource://" + getPackageName() + "/" + autoResponseResIds[randomIndex];
        String autoResponseName = autoName[randomIndex % autoName.length];  // Use randomIndex to pick a name
        ChatMessage autoResponse = new ChatMessage(autoResponseName, autoResponseUrl);
        String responseId = databaseReference.push().getKey();
        if (responseId != null) {
            databaseReference.child(responseId).setValue(autoResponse);
            chatViewModel.insertMessage(autoResponse);
        }
    }

 }
