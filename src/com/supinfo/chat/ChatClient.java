/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Alexis
 */
public class ChatClient extends Application {
    private AnchorPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Class.forName(com.mysql.jdbc.Driver.class.getName());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found !");
        }

        final FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/supinfo/chat/views/chat.fxml")
        );
        
        root = loader.load();
        
        final Scene scene = new Scene(root);
        
        primaryStage.setTitle("Supchat");
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }    
}
