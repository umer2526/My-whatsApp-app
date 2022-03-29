package com.example.whatsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.R;
import com.example.whatsapp.adapters.ChatAdapters;
import com.example.whatsapp.databinding.ActivityChatDetailsBinding;
import com.example.whatsapp.modelss.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderID = auth.getUid();
        String receiverID = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profilePicture = getIntent().getStringExtra("profilePicture");

        binding.userName.setText(userName);
        Picasso.get().load(profilePicture).placeholder(R.drawable.user).into(binding.profileImage);

        setListeners();

        final ArrayList<Messages> messageM = new ArrayList<>();
        final ChatAdapters chatAdapters = new ChatAdapters(messageM,this, receiverID);
        binding.chatRecyclerView.setAdapter(chatAdapters);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);

        final String senderRoom = senderID + receiverID;
        final String receiverRoom = receiverID + senderID;

        database.getReference().child("Chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageM.clear();
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            Messages messages = dataSnapshot1.getValue(Messages.class);

                            messages.setMessageID(dataSnapshot1.getKey());

                            messageM.add(messages);
                        }
                        chatAdapters.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String message = binding.writeMsg.getText().toString();
                    final Messages messages = new Messages(senderID ,message);
                    messages.setTimeStamp(new Date().getTime());
                    binding.writeMsg.setText("");

                    database.getReference().child("Chats")
                            .child(senderRoom)
                            .push()
                            .setValue(messages)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("Chats")
                                            .child(receiverRoom)
                                            .push()
                                            .setValue(messages)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                }
                            });
            }
        });
    }

    private void setListeners(){
        binding.back.setOnClickListener(v -> onBackPressed());
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}