/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Josip
 */
public class FileUtils {
    
    private static final String UPLOAD = "Upload";
    private static final String OPEN_FOLDER = "Open folder";

    public static File uploadFile(String description, String...extensions) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        fileChooser.setDialogTitle(UPLOAD);
        fileChooser.setApproveButtonText(UPLOAD);
        fileChooser.setApproveButtonToolTipText(UPLOAD);
          
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            String filename = selectedFile.getName();
            String ext = filename.substring(filename.lastIndexOf(".") +1);
            return Arrays.asList(extensions).contains(ext) ? selectedFile : null;
        }
        return null;
    }
    
    public static File saveFile(){
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle(OPEN_FOLDER);
        fileChooser.setApproveButtonText(OPEN_FOLDER);
        fileChooser.setApproveButtonToolTipText(OPEN_FOLDER);
        
        
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File selectedFolder = fileChooser.getSelectedFile();
            return selectedFolder;
        }
        return null;
    }

    public static void copyContentFromUrl(String urlSource, String fileDestination) throws IOException {
        String localDir = fileDestination.substring(0, fileDestination.lastIndexOf(File.separator));
        
        if (!Files.exists(Paths.get(localDir))) {
            Files.createDirectory(Paths.get(localDir));
        }
        URL url = new URL(urlSource);
        try(InputStream in = url.openStream()){
            Files.copy(in, Paths.get(fileDestination));
        }
    }

    public static void copy(String source, String destination) throws IOException {
        String localDir = destination.substring(0, destination.lastIndexOf(File.separator));
        
        if (!Files.exists(Paths.get(localDir))) {
            Files.createDirectory(Paths.get(localDir));
        }
        Files.copy(Paths.get(source), Paths.get(destination));
    }

    public static boolean filenameHasExtension(String filename, int length) {
        return !filename.isEmpty() && filename.contains(".") && filename.substring(filename.indexOf(".")+1).length() == length;
    }
    
    
    public static void cleanDirectory(String dirPath){
        if (!Files.exists(Paths.get(dirPath))) {
            return;
        }
        
        File directory = new File(dirPath);
        for (File file : directory.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }   
    }
    
    
}
