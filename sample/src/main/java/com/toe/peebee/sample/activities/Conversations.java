package com.toe.peebee.sample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.toe.peebee.PeeBee;
import com.toe.peebee.constructors.ConversationItem;
import com.toe.peebee.interfaces.OnConversationChangedListener;
import com.toe.peebee.sample.R;
import com.toe.peebee.sample.adapters.ConversationsRecyclerViewAdapter;
import com.toe.peebee.sample.classes.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class Conversations extends AppCompatActivity implements OnConversationChangedListener {

    private String TAG = "PeeBeeChats";

    //Related to the RecyclerView
    private RecyclerView conversationRecyclerView;
    private RecyclerView.Adapter conversationAdapter;

    //ArrayLists
    private ArrayList<String> conversationKeys = new ArrayList<>();

    //Widgets
    private FloatingActionButton fabAddConversation;

    //Others
    private PeeBee peeBee;
    private Intent intent;

    private String userId = "Bonbibel Bubblegum";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversations);

        setUpPeeBee();
        setUpViews();
    }

    private void setUpPeeBee() {
        peeBee = new PeeBee(userId);
        peeBee.setOnConversationsChangedListener(this);
    }

    private void setUpViews() {
        LinearLayoutManager chatsLayoutManager = new LinearLayoutManager(getApplicationContext());
        conversationRecyclerView = (RecyclerView) findViewById(R.id.rvChats);
        conversationRecyclerView.setLayoutManager(chatsLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {
                        intent = new Intent(getApplicationContext(), Messages.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("key", conversationKeys.get(position));
                        intent.putExtras(bundle);

                        startActivity(intent);

                        // How to delete conversation
//                        peeBee.deleteConversation(conversationKeys.get(position));
                    }
                }));

        fabAddConversation = (FloatingActionButton) findViewById(R.id.fabAddConversation);
        fabAddConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peeBee.createConversation("Finn the Human");
            }
        });
    }

    @Override
    public void onConversationChanged(ArrayList<ConversationItem> conversations, ArrayList<String> conversationKeys) {
        this.conversationKeys = conversationKeys;

        conversationAdapter = new ConversationsRecyclerViewAdapter(Conversations.this, conversations);
        conversationRecyclerView.setAdapter(conversationAdapter);
        conversationAdapter.notifyDataSetChanged();
    }
}

