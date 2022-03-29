package com.example.whatsapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.R;
import com.example.whatsapp.modelss.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapters extends RecyclerView.Adapter {

    ArrayList<Messages> messagesModel;
    Context context;
    String recID;
    int RVT = 2;
    int SVT = 1;

    public ChatAdapters(ArrayList<Messages> messagesModel, Context context) {
        this.messagesModel = messagesModel;
        this.context = context;
    }

    public ChatAdapters(ArrayList<Messages> messagesModel, Context context, String recID) {
        this.messagesModel = messagesModel;
        this.context = context;
        this.recID = recID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SVT){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (messagesModel.get(position).getiId().equals(FirebaseAuth.getInstance().getUid())){
            return SVT;
        }else{
            return RVT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages messages = messagesModel.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Kia app message delete karna chahte hain ??")
                        .setPositiveButton("Han", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recID;
                                database.getReference().child("Chats").child(senderRoom).child(messages.getMessageID()).setValue(null);

                            }
                        }).setNegativeButton("Nhi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

                return false;
            }
        });

        if (holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMsg.setText(messages.getMessage());
        }else {
            ((RecieverViewHolder)holder).receiverMeg.setText(messages.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messagesModel.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView receiverMeg, receiverTime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMeg = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }

}
