package com.example.android;

public interface IMessageNotifier {

    void addMessageListener(IMessageListener ml);

    void removeMessageListener(IMessageListener ml);

}
