/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import org.apache.commons.validator.routines.UrlValidator;


/**
 *
 * @author Josip
 */
public class UrlUtils {
    public static boolean UrlIsValid(String url){
        //String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_2_SLASHES);
        
        return urlValidator.isValid(url);
    }
}
