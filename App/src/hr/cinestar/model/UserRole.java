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
public enum UserRole {
        ADMIN (1),
        USER (2);
       
        private final int roleId;
        
        private UserRole(int role){
            roleId = role;
        }
        
       public int getRoleId(){
            return roleId;
        }
       
       public static UserRole from (int role){
           switch(role){
               case 1:
                   return ADMIN;
               case 2:
                   return USER;
           }
           
           return USER;
       }
    }
