/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat.threads;

import com.supinfo.chat.MessagesService;
import com.supinfo.chat.db.LogsDao;
import com.supinfo.chat.models.Message;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 *
 * @author Alexis
 */
public class SocketThread implements Runnable {
    private final ObservableList<String> members;
    private final ObservableList<Message> messages;
    private final LogsDao logsDao = LogsDao.getInstance();

    public SocketThread(ObservableList<String> members, ObservableList<Message> messages) {
        this.members = members;
        this.messages = messages;
    }

    @Override
    public void run() {
        try(
            Socket socket = new Socket(InetAddress.getLocalHost(), 18000);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String serverInput;
            
            MessagesService.getInstance().subscribe(msg -> {
                out.println(msg);
                out.flush();
            });
            
            out.println("username:Zarkus");
            out.flush();
            
            while((serverInput = in.readLine()) != null) {
                final String[] arr = serverInput.split(":::");
                
                if(arr.length == 2) {
                    final String username = arr[0];
                    final String msg = arr[1];

                    this.logsDao.saveLog(username, msg);
                    
                    Platform.runLater(() -> {
                        this.messages.add(new Message(username, msg));
                    });

                    System.out.println("<" + username + "> : " + msg);
                } else {
                    System.out.println(serverInput);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}
