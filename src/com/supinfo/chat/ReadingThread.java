/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat;

import com.supinfo.chat.db.LogsDao;
import java.io.BufferedReader;

/**
 *
 * @author Alexis
 */
public class ReadingThread implements Runnable {
    private final BufferedReader in;
    private final LogsDao logsDao;

    public ReadingThread(BufferedReader in) {
        this.in = in;
        this.logsDao = LogsDao.getInstance();
    }
    
    @Override
    public void run() {
        String serverInput;
                
        try {
            while((serverInput = in.readLine()) != null) {
                final String[] arr = serverInput.split(":::");
                
                if(arr.length == 2) {
                    final String username = arr[0];
                    final String msg = arr[1];

                    this.logsDao.saveLog(username, msg);

                    System.out.println("<" + username + "> : " + msg);
                } else {
                    System.out.println(serverInput);
                }
            }
        } catch (Exception e) {
            System.out.println("Connexion closed");
        }
    }
    
}
