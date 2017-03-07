/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat;

import com.supinfo.chat.db.LogsDao;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Alexis
 */
public class ChatClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName(com.mysql.jdbc.Driver.class.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found !");
        }
        
        final LogsDao logsDao = LogsDao.getInstance();
        
        try(
            Socket socket = new Socket(InetAddress.getLocalHost(), 18000);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String userInput;
            
            new Thread(new ReadingThread(in)).start();
            
            out.println("username:Zarkus");
            out.flush();
            
            while((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                out.flush();
                
                logsDao.saveLog("You", userInput);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
