/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat.controllers;

import com.supinfo.chat.MessagesService;
import com.supinfo.chat.db.LogsDao;
import com.supinfo.chat.models.Message;
import com.supinfo.chat.threads.SocketThread;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Alexis
 */
public class ChatController implements Initializable {
    
    private final LogsDao logsDao = LogsDao.getInstance();
    private ObservableList<String> members = FXCollections.observableArrayList();
    private ObservableList<Message> messages = FXCollections.observableArrayList();

    @FXML
    private ListView<String> membersListView = new ListView<>();
    
    @FXML
    private ListView<Message> messagesListView = new ListView<>();
    
    @FXML
    private TextField inputText = new TextField();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.membersListView.setItems(this.members);
        this.messagesListView.setItems(this.messages);
        
        Thread t = new Thread(new SocketThread(this.members, this.messages));
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void onKeyReleased(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            MessagesService.getInstance().newMessage(this.inputText.getText());
            this.inputText.setText("");
        }
    }
    
}
