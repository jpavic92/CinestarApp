/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.dal;

import hr.cinestar.dal.sql.SqlRepository;

/**
 *
 * @author Josip
 */
public class RepositoryFactory {
    
    private static final Repository INSTANCE = new SqlRepository();
    
    public static Repository getRepository(){
        return INSTANCE;
    }

    private RepositoryFactory() {
    }
    
}
