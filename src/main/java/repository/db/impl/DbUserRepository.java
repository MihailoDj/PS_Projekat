/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.db.DbRepository;

/**
 *
 * @author Mihailo
 */
public class DbUserRepository implements DbRepository<User>{

    @Override
    public List<User> getAll() throws Exception{
        String url = "jdbc:mysql://localhost:3306/psdb";
        String username = "root";
        String password = "";
        
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            List<User> users = new ArrayList<User>();
            
            String sql = "SELECT * FROM user";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                User user = new User() {
                    {
                        setUserID(rs.getInt("userID"));
                        setUsername(rs.getString("username"));
                        setPassword(rs.getString("password"));
                        setAdmin(rs.getBoolean("admin"));
                    }
                };
                users.add(user);
            }
            
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Connection error!");
        }
    }
    
}
