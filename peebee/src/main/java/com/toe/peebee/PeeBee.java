package com.toe.peebee;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toe.peebee.constructors.ConversationItem;
import com.toe.peebee.constructors.MessageItem;
import com.toe.peebee.interfaces.OnConversationChangedListener;
import com.toe.peebee.interfaces.OnMessageChangedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ian on 21/07/2016.
 */
public class PeeBee {

    private static String TAG = "PeeBeeCore";

    //Strings
    private String userId;

    //Firebase
    private DatabaseReference firebaseReference;

    //Interfaces
    private OnConversationChangedListener conversationsChangedListener;
    private OnMessageChangedListener messagesChangedListener;

    //Arrays
    private ArrayList<ConversationItem> conversations = new ArrayList<>();
    private ArrayList<String> conversationKeys = new ArrayList<>();
    private ArrayList<MessageItem> messages = new ArrayList<>();
    private ArrayList<String> messageKeys = new ArrayList<>();


    public PeeBee(String userId) {
        this.userId = userId;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseReference = database.getReference("conversations");
    }

    public void createConversation(String to) {
        firebaseReference.push().setValue(new ConversationItem(userId, to, generateTimestamp()));
    }

    public void sendMessage(String conversationKey, String to, String message) {
        firebaseReference.child(conversationKey).child("messages").push().setValue(new MessageItem(message, userId, to, generateTimestamp()));
    }

    private static String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String timestamp = dateFormat.format(new Date());

        return timestamp;
    }

    public void setOnConversationsChangedListener(OnConversationChangedListener conversationsChangedListener) {
        this.conversationsChangedListener = conversationsChangedListener;
        listenToConversations();
    }

    private void listenToConversations() {
        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversations.clear();
                conversationKeys.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ConversationItem chat = postSnapshot.getValue(ConversationItem.class);

                    if (chat.getFrom().equals(userId) || chat.getTo().equals(userId)) {
                        conversations.add(chat);
                        conversationKeys.add(postSnapshot.getKey());
                    }
                }

                conversationsChangedListener.onConversationChanged(conversations, conversationKeys);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void setOnMessagesChangedListener(String conversationKey, OnMessageChangedListener messagesChangedListener) {
        this.messagesChangedListener = messagesChangedListener;
        listenToMessages(conversationKey);
    }

    private void listenToMessages(String conversationKey) {
        firebaseReference.child(conversationKey).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages.clear();
                messageKeys.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MessageItem message = postSnapshot.getValue(MessageItem.class);

                    messages.add(message);
                    messageKeys.add(postSnapshot.getKey());
                }

                messagesChangedListener.onMessagesChanged(messages, messageKeys);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void deleteConversation(String conversationKey) {
        firebaseReference.child(conversationKey).removeValue();
    }

    public void deleteMessage(String conversationKey, String messageKey) {
        firebaseReference.child(conversationKey).child("messages").child(messageKey).removeValue();
    }
}
