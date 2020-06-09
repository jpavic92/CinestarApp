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
public enum MovieRole {
    DIRECTOR(1),
    ACTOR(2);
    
    
    private final int roleN;
    
    private MovieRole(int roleN){
        this.roleN = roleN;
    }
    
    public int getRole(){
        return roleN;
    }

    public static MovieRole from (int role){
        switch(role){
            case 1:
                return DIRECTOR;
            case 2:
                return ACTOR;
        }
        return ACTOR;
    }
}
