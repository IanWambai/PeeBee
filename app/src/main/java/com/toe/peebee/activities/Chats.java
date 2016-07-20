package com.toe.peebee.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toe.peebee.R;
import com.toe.peebee.adapters.ChatsRecyclerViewAdapter;
import com.toe.peebee.classes.RecyclerItemClickListener;
import com.toe.peebee.constructors.ChatListItem;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class Chats extends AppCompatActivity {

    private String TAG = "PeeBeeChats";

    //Related to the RecyclerView
    private RecyclerView chatsRecyclerView;
    private RecyclerView.Adapter chatsAdapter;

    //ArrayLists
    private ArrayList<ChatListItem> chats = new ArrayList<>();

    //Firebase
    private DatabaseReference firebaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stores);

        setUpFirebase();
        setUpViews();
        getChats();
    }

    private void setUpFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseReference = database.getReference("conversations");
    }

    private void setUpViews() {
        LinearLayoutManager chatsLayoutManager = new LinearLayoutManager(getApplicationContext());
        chatsRecyclerView = (RecyclerView) findViewById(R.id.rvChats);
        chatsRecyclerView.setLayoutManager(chatsLayoutManager);
        chatsRecyclerView.setHasFixedSize(true);
        chatsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {

                    }
                }));

        chatsAdapter = new ChatsRecyclerViewAdapter(Chats.this, chats);
    }

    private void getChats() {
        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ChatListItem chat = postSnapshot.getValue(ChatListItem.class);
                    chats.add(chat);
                }

                populateChats(chats);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void populateChats(final ArrayList<ChatListItem> chats) {
        if (chats.size() > 0) {
            chatsAdapter.notifyDataSetChanged();
        } else {
            chatsAdapter.notifyDataSetChanged();
        }
    }
}

