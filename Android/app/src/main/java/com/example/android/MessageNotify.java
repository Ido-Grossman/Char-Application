package com.example.android;

import java.util.ArrayList;
import java.util.List;

public class MessageNotify implements IMessageNotifier {

    private List<IMessageListener> listeners;


    public MessageNotify() {
        listeners = new ArrayList<>();
    }

    // Notifies all the listeners.
    public void NotifyAll(String userId, String message) {
        for (IMessageListener listener : listeners) {
            listener.messageEvent();
        }
    }

    // Adds a new message listener
    @Override
    public void addMessageListener(IMessageListener ml) {
        listeners.add(ml);
    }

    // Removes message listener.
    @Override
    public void removeMessageListener(IMessageListener ml) {
        listeners.add(ml);
    }

}
