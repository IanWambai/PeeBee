package com.toe.peebee.interfaces;

import com.toe.peebee.constructors.ConversationItem;

import java.util.ArrayList;

/**
 * Created by ian on 21/07/2016.
 */
public interface OnConversationChangedListener {
    void onConversationChanged(ArrayList<ConversationItem> conversations, ArrayList<String> conversationKeys);
}
