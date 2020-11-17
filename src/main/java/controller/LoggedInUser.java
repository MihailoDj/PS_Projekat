/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.User;

/**
 *
 * @author Mihailo
 */
public class LoggedInUser {
    private static LoggedInUser loggedInUser;
    private User user;
    
    private LoggedInUser() {
        
    }
    
    public static LoggedInUser getInstance() {
        if (loggedInUser == null)
            loggedInUser = new LoggedInUser();
        
        return loggedInUser; 
    }
    
    public void setLoggedInUser(User user) {
        this.user = user;
    }
    
    public User getLoggedInUser() {
        return user;
    }
}
