package com.toe.peebee.sample.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.toe.peebee.PeeBee;
import com.toe.peebee.constructors.MessageItem;
import com.toe.peebee.interfaces.OnMessageChangedListener;
import com.toe.peebee.sample.R;
import com.toe.peebee.sample.adapters.MessagesRecyclerViewAdapter;
import com.toe.peebee.sample.classes.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class Messages extends AppCompatActivity implements OnMessageChangedListener {

    private String TAG = "PeeBeeChats";

    //Related to the RecyclerView
    private RecyclerView messageRecyclerView;
    private RecyclerView.Adapter messageAdapter;

    //ArrayLists
    private ArrayList<String> messageKeys = new ArrayList<>();

    //Widgets
    private FloatingActionButton fabAddMessage;

    //Strings
    private String userId = "Bonbibel Bubblegum";
    private String conversationKey;

    //Others
    private PeeBee peeBee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversations);

        getKey();
        setUpPeeBee();
        setUpViews();
    }

    private void getKey() {
        Bundle bundle = getIntent().getExtras();
        conversationKey = bundle.getString("key");
    }

    private void setUpPeeBee() {
        peeBee = new PeeBee(userId);
        peeBee.setOnMessagesChangedListener(conversationKey, this);
    }

    private void setUpViews() {
        LinearLayoutManager chatsLayoutManager = new LinearLayoutManager(getApplicationContext());
        messageRecyclerView = (RecyclerView) findViewById(R.id.rvChats);
        messageRecyclerView.setLayoutManager(chatsLayoutManager);
        messageRecyclerView.setHasFixedSize(true);
        messageRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {
                        peeBee.deleteMessage(conversationKey, messageKeys.get(position));
                    }
                }));

        fabAddMessage = (FloatingActionButton) findViewById(R.id.fabAddConversation);
        fabAddMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peeBee.sendMessage(conversationKey, "Finn The Human", "Whoa yoah!");
            }
        });
    }

    @Override
    public void onMessagesChanged(ArrayList<MessageItem> messages, ArrayList<String> messageKeys) {
        this.messageKeys = messageKeys;

        messageAdapter = new MessagesRecyclerViewAdapter(Messages.this, messages);
        messageRecyclerView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }
}

