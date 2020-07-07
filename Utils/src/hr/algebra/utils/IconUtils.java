/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Josip
 */
public class IconUtils {

    public static ImageIcon createIcon(String path, int width, int height) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(path));
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    
     public static ImageIcon createIcon(URL url, int width, int height) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(url);
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    
}
