/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.factory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Josip
 */
public class UrlConnectionFactory {

    public static HttpURLConnection getHttpUrlConnection(String path, int timeout, String req_method) throws MalformedURLException, IOException {
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(req_method);
        con.setConnectTimeout(timeout);
        con.setReadTimeout(timeout);
        con.addRequestProperty("User-Agent", "Mozilla/5.0");
        con.connect();
        
        return con;
    }
    
}
