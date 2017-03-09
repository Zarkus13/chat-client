/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexis
 */
public class MessagesService {
    private static MessagesService instance;
    
    private MessagesService() { }
    
    public static MessagesService getInstance() {
        if(instance == null)
            instance = new MessagesService();
        
        return instance;
    }
    
    public interface MessagesListener {
        void onNewMessage(final String message);
    }
    
    private List<MessagesListener> listeners = new ArrayList<>();
    
    public void subscribe(final MessagesListener listener) {
        this.listeners.add(listener);
    }
    
    public void newMessage(final String message) {
        this.listeners
            .forEach((listener) -> listener.onNewMessage(message));
    }
    
}
