/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

import java.util.Arrays;

/**
 *
 * @author Josip
 */
public class User {
    
    private final String username;
    private final Integer password;
    private UserRole userRole;
    
    public User (String username, char[] password){
        this.username = username;
        this.password = Arrays.hashCode(password);
    }
    
    public User (String username, char[] password, UserRole role){
        this.username = username;
        this.password = Arrays.hashCode(password);
        userRole = role;
    } 
    
    public User (String username, Integer password, UserRole role){
        this.username = username;
        this.password = password;
        userRole = role;
    } 

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password.toString();
    }
    
    public UserRole getRole(){
        return userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            return ((User)o).getUsername().equals(username);
        }
        return false;
    }
    
}
