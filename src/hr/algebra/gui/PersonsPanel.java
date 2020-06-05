/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.gui;

import hr.algebra.utils.MessageUtils;
import hr.cinestar.dal.Repository;
import hr.cinestar.dal.RepositoryFactory;
import hr.cinestar.model.Movie;
import hr.cinestar.model.MovieRole;
import hr.cinestar.model.Person;
import hr.cinestar.model.PersonTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Josip
 */
public class PersonsPanel extends javax.swing.JPanel {
    
    private List<JTextField> validationFields;
    private List<JLabel> errorLabels;
    
    private Repository repo;
    private List<Person> persons;
    private PersonTableModel personsModel;
    
    private Person selectedPerson;
    private Map<Integer, Movie> allMovies;
    private final List<Movie> moviesDirected = new ArrayList<>();
    private final List<Movie> moviesActed  = new ArrayList<>();
    
    private final DefaultListModel<Movie> moviesDirectedModel = new DefaultListModel<>();
    private final DefaultListModel<Movie> moviesActedModel = new DefaultListModel<>();
    
    /**
     * Creates new form PersonsPanel
     */
    public PersonsPanel() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        tfFirstName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfLastName = new javax.swing.JTextField();
        lblFirstNameError = new javax.swing.JLabel();
        lblLastNameError = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lsActs = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        lsDirects = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPersons = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAddActed = new javax.swing.JButton();
        btnRemoveActed = new javax.swing.JButton();
        btnAddDirected = new javax.swing.JButton();
        btnRemoveDirected = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        tfFilterNames = new javax.swing.JTextField();
        rbFirstName = new javax.swing.JRadioButton();
        rbLastName = new javax.swing.JRadioButton();

        setMaximumSize(new java.awt.Dimension(665, 430));
        setPreferredSize(new java.awt.Dimension(665, 430));

        jLabel1.setText("First name:");

        jLabel2.setText("Last name:");

        lblFirstNameError.setForeground(new java.awt.Color(250, 0, 0));

        lblLastNameError.setForeground(new java.awt.Color(250, 0, 0));

        jLabel6.setText("Acts in:");

        jLabel7.setText("Movies directed:");

        jScrollPane4.setViewportView(lsActs);

        jScrollPane5.setViewportView(lsDirects);

        tbPersons.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        tbPersons.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbPersonsMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbPersons);

        btnAdd.setText("Add a celeb");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update a celeb");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete a celeb");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAddActed.setText("Add");
        btnAddActed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActedActionPerformed(evt);
            }
        });

        btnRemoveActed.setText("Remove");
        btnRemoveActed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActedActionPerformed(evt);
            }
        });

        btnAddDirected.setText("Add");
        btnAddDirected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDirectedActionPerformed(evt);
            }
        });

        btnRemoveDirected.setText("Remove");
        btnRemoveDirected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveDirectedActionPerformed(evt);
            }
        });

        jLabel3.setText("Search by:");

        tfFilterNames.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfFilterNamesKeyReleased(evt);
            }
        });

        buttonGroup.add(rbFirstName);
        rbFirstName.setSelected(true);
        rbFirstName.setText("First name");
        rbFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFilterNameActionPerformed(evt);
            }
        });

        buttonGroup.add(rbLastName);
        rbLastName.setText("Last name");
        rbLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFilterNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfFilterNames, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbFirstName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbLastName))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(89, 89, 89)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLastNameError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFirstNameError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddActed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveActed))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddDirected)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveDirected)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblFirstNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLastNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddActed)
                            .addComponent(btnRemoveActed))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAddDirected)
                            .addComponent(btnRemoveDirected)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(rbFirstName)
                            .addComponent(rbLastName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfFilterNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnUpdate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)))
                .addContainerGap(115, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbPersonsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPersonsMouseReleased
        int row = tbPersons.getSelectedRow();
        int personId = (int)tbPersons.getValueAt(row, 0);
        
        try {
            Optional<Person> optPerson = repo.selectPerson(personId);
            selectedPerson = optPerson.get();
            fillForm();
        } catch (Exception ex) {
            Logger.getLogger(PersonsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbPersonsMouseReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!formValid()) {
            return;
        }
        Person newCeleb = new Person(
                tfFirstName.getText().trim(), 
                tfLastName.getText().trim());
        
        if (!persons.contains(newCeleb)) {
            try {
                int newId = repo.createPerson(newCeleb);
                newCeleb.setId(newId);
                addMovieInvolvements(newId);
                MessageUtils.showInformationMessage("Success!", "New celeb name has been added");
                loadPersonsModel();
            } catch (Exception ex) {
                Logger.getLogger(PersonsPanel.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error!", "Unable to create new celeb.");
            }
        }
        else MessageUtils.showInformationMessage("Dublicate data", "Entered full name already exists in our database");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selectedPerson  == null) {
            MessageUtils.showInformationMessage("No movie selected", "Please select a movie.");
            return;
        }
        
        if (formValid() && MessageUtils.showConfirmationMessage("Update a celeb", "Celeb data will be changed. Do you agree?") == JOptionPane.YES_OPTION) {
            try {
                selectedPerson.setFirstName(tfFirstName.getText().trim());
                selectedPerson.setLastName(tfLastName.getText().trim());
                repo.updatePerson(selectedPerson.getId(), selectedPerson);
                addMovieInvolvements(selectedPerson.getId());
                MessageUtils.showInformationMessage("Success!", "Data changed.");
                loadPersonsModel();
            } catch (Exception ex) {
                Logger.getLogger(PersonsPanel.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error!", "Unable to update selected celeb ");
            }
        }
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
         if (selectedPerson  == null) {
            MessageUtils.showInformationMessage("No movie selected", "Please select a celeb name.");
            return;
        }
         
        if (MessageUtils.showConfirmationMessage("Delete?", selectedPerson.toString() + " will be removed from all lists.") == JOptionPane.YES_OPTION) {
            try {
                repo.deletePerson(selectedPerson.getId());
                MessageUtils.showInformationMessage("Success!", selectedPerson.toString() + " has been removed.");
                loadPersonsModel();
                clearForm();
            } catch (Exception ex) {
                Logger.getLogger(PersonsPanel.class.getName()).log(Level.SEVERE, null, ex);
                MessageUtils.showErrorMessage("Error!", "Unable to remove selected celeb.");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActedActionPerformed
        new AddMovieDialog((JFrame)this.getRootPane().getParent(), false, this, moviesActed, allMovies, MovieRole.ACTOR).setVisible(true);
    }//GEN-LAST:event_btnAddActedActionPerformed

    private void btnRemoveActedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActedActionPerformed
        lsActs.getSelectedValuesList().forEach(movie -> {
            moviesActed.remove(movie); 
            moviesActedModel.removeElement(movie);
        });
    }//GEN-LAST:event_btnRemoveActedActionPerformed

    private void btnAddDirectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDirectedActionPerformed
        new AddMovieDialog((JFrame)this.getRootPane().getParent(), false, this, moviesDirected, allMovies, MovieRole.DIRECTOR).setVisible(true);
    }//GEN-LAST:event_btnAddDirectedActionPerformed

    private void btnRemoveDirectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveDirectedActionPerformed
        lsDirects.getSelectedValuesList().forEach(movie -> {
            moviesDirected.remove(movie); 
            moviesDirectedModel.removeElement(movie);
        });
    }//GEN-LAST:event_btnRemoveDirectedActionPerformed

    private void tfFilterNamesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfFilterNamesKeyReleased
        filterPersons();
    }//GEN-LAST:event_tfFilterNamesKeyReleased

    private void rbFilterNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFilterNameActionPerformed
        if (!tfFilterNames.getText().isEmpty()) {
            filterPersons();
        }
    }//GEN-LAST:event_rbFilterNameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddActed;
    private javax.swing.JButton btnAddDirected;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRemoveActed;
    private javax.swing.JButton btnRemoveDirected;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblFirstNameError;
    private javax.swing.JLabel lblLastNameError;
    private javax.swing.JList<Movie> lsActs;
    private javax.swing.JList<Movie> lsDirects;
    private javax.swing.JRadioButton rbFirstName;
    private javax.swing.JRadioButton rbLastName;
    private javax.swing.JTable tbPersons;
    private javax.swing.JTextField tfFilterNames;
    private javax.swing.JTextField tfFirstName;
    private javax.swing.JTextField tfLastName;
    // End of variables declaration//GEN-END:variables

    private void init() {
        
        try {
            initValidation();
            initRepo();
            initTable();
            initMovies();
        } catch (Exception ex) {
            Logger.getLogger(PersonsPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage("Error", "Unable to initiate the form");
        }
    }

    private void initValidation() {
        validationFields = Arrays.asList(tfFirstName, tfLastName);
        errorLabels = Arrays.asList(lblFirstNameError, lblLastNameError);
    }

    private boolean formValid() {
        boolean ok = true;
        for (int i = 0; i < validationFields.size(); i++) {
            ok &= !validationFields.get(i).getText().isEmpty();
            errorLabels.get(i).setText(validationFields.get(i).getText().isEmpty() ? "X" : "");
        }
        return ok;
    }

    private void initRepo() {
        repo = RepositoryFactory.getRepository();
    }

    private void loadPersonsModel() throws Exception {
        persons = repo.selectPersons();
        personsModel.setPersons(persons);
        filterPersons();
    }
    
    private void filterPersons(){
        List<Person> filtered = rbFirstName.isSelected() ?  
                persons.stream().filter(person -> person.getFirstName().toLowerCase().startsWith(tfFilterNames.getText().toLowerCase().trim())).collect(Collectors.toList())
                :
                persons.stream().filter(person -> person.getLastName().toLowerCase().startsWith(tfFilterNames.getText().toLowerCase().trim())).collect(Collectors.toList());
        
        personsModel.setPersons(filtered);
    }

    private void initTable() throws Exception {
        tbPersons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbPersons.setAutoCreateRowSorter(true);
        tbPersons.setRowHeight(26);
        persons = repo.selectPersons();
        personsModel = new PersonTableModel(persons);
        tbPersons.setModel(personsModel);
        tbPersons.getColumnModel().getColumn(0).setMaxWidth(45);
    }

    private void fillForm() throws Exception {
        tfFirstName.setText(selectedPerson.getFirstName());
        tfLastName.setText(selectedPerson.getLastName());
        loadMoviesDirected();
        loadMoviesActed();
    }
    
     private void loadMoviesDirected() throws Exception {
        List<Integer> moviesIds = repo.selectMoviesIdByPersonRole(selectedPerson.getId(), MovieRole.DIRECTOR.getRole());
        moviesDirected.clear();
        moviesIds.forEach(id -> moviesDirected.add(allMovies.get(id)));
        loadMovieDirectedModel();
    }

    private void loadMoviesActed() throws Exception {
        List<Integer> moviesIds = repo.selectMoviesIdByPersonRole(selectedPerson.getId(), MovieRole.ACTOR.getRole());
        moviesActed.clear();
        moviesIds.forEach(id -> moviesActed.add(allMovies.get(id)));
        loadMovieActedModel();
    }
    
    private void loadMovieActedModel(){
        moviesActedModel.clear();
        moviesActed.forEach(movie -> moviesActedModel.addElement(movie));
        lsActs.setModel(moviesActedModel);
    }
    
    private void loadMovieDirectedModel(){
        moviesDirectedModel.clear();
        moviesDirected.forEach(movie -> moviesDirectedModel.addElement(movie));
        lsDirects.setModel(moviesDirectedModel);
    }

    private void initMovies() throws Exception {
        allMovies = new HashMap<>();
        List<Movie> movies = repo.selectMovies();
        movies.forEach(movie -> allMovies.put(movie.getId(), movie));
    }

    public void addMovieDirected(Movie movie){
        if (moviesDirected.add(movie)) {
            loadMovieDirectedModel();
        }
    }
    
    public void addMovieActed(Movie movie){
        if (moviesActed.add(movie)) {
            loadMovieActedModel();
        }
    }

    private void addMovieInvolvements(int personId) throws Exception {
        repo.createPersonsInvolvements(personId, moviesDirected, moviesActed);
    }

    private void clearForm() {
        tfFirstName.setText("");
        tfLastName.setText("");
        moviesActed.clear();
        loadMovieActedModel();
        moviesDirected.clear();
        loadMovieDirectedModel();
    }
}
