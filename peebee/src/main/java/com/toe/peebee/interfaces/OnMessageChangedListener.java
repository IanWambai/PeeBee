package com.toe.peebee.interfaces;

import com.toe.peebee.constructors.MessageItem;

import java.util.ArrayList;

/**
 * Created by ian on 21/07/2016.
 */
public interface OnMessageChangedListener {
    void onMessagesChanged(ArrayList<MessageItem> messages, ArrayList<String> messageKeys);
}
