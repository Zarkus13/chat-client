/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supinfo.chat.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Alexis
 */
public class LogsDao {
    private static LogsDao instance;
    
    private LogsDao() {}
    
    public static LogsDao getInstance() {
        if(instance == null)
            instance = new LogsDao();
        
        return instance;
    }
    
    public void saveLog(final String username, final String message) {
        try(
            Connection cnx = ConnectionFactory.getConnection()
        ) {
            cnx.setAutoCommit(false);
            
            try {
                PreparedStatement stmt = cnx.prepareStatement("INSERT INTO logs(username, message) VALUES(?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, message);

                stmt.executeUpdate();

                cnx.commit();
            } catch (SQLException e) {
                cnx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
