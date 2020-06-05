/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

/**
 *
 * @author Josip
 */
public class User {
    private final String username;
    private final Integer password;
    
    public User (String username, String password){
        this.username = username;
        this.password = password.hashCode();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password.toString();
    }
    
    
    
}
