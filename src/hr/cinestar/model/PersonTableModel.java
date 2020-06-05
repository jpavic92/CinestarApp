/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.cinestar.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Josip
 */
public class PersonTableModel extends AbstractTableModel{
    
    private static final String[] COLUMNS = {"Id", "Celeb name"};
    private List<Person> persons;
    
    public PersonTableModel(List<Person> persons){
        this.persons = persons;
    }
    
    
    public void setPersons(List<Person> persons){
        this.persons = persons;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
       return persons.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch(col){
            case 0:
                return persons.get(row).getId();
            case 1:
                return persons.get(row).toString();
                default:
            throw new RuntimeException("No such column");
        }
    }

    @Override
    public String getColumnName(int col) {
        return COLUMNS[col];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        switch(col){
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(col);
    }
    
    

    

    
    
    
    
}
