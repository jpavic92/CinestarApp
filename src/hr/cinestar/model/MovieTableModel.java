/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Josip
 */
public class MovieTableModel extends AbstractTableModel  {
    
    private List<Movie> movies;
    private static final String[] COLUMNS = {"Id", "Title"};
    
    public MovieTableModel(List<Movie> movies){
        this.movies = movies;
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch(col){
            case 0:
                return movies.get(row).getId();
            case 1:
                return movies.get(row).getTitle();
            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public String getColumnName(int i) {
        return COLUMNS[i];
    }
    
    
    
}
