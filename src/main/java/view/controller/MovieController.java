/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import controller.Controller;
import domain.Actor;
import domain.Director;
import domain.Movie;
import domain.Role;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmMovie;
import view.form.component.table.RoleTableModel;
import view.form.util.FormMode;

/**
 *
 * @author Mihailo
 */
public class MovieController {
    private final FrmMovie frmMovie;
    private List<Role> roles;

    public MovieController(FrmMovie frmMovie) {
        this.frmMovie = frmMovie;
        roles = new ArrayList<Role>();
        addActionListeners();
        addListSelectionListener();
    }

    private void addActionListeners() {
        frmMovie.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblRoles(roles);
            }
        });
        frmMovie.addBtnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
            
            private void add() {
                try {
                    validateForm();
                    Movie movie = new Movie() {
                        {
                            setName(frmMovie.getTxtName().getText().trim());
                            setDescription(frmMovie.getTxtDescription().getText().trim());
                            setScore(Double.parseDouble(frmMovie.getTxtScore().getText()));
                            setDirector((Director) frmMovie.getCbDirector().getSelectedItem());
                            setReleaseDate(frmMovie.getReleaseDate().getDate());
                        }
                    };
                    
                    Controller.getInstance().insertMovie(movie);
                    JOptionPane.showMessageDialog(frmMovie, "Movie successfully saved!");
                    frmMovie.dispose();
                } catch (Exception ex) {
                    Logger.getLogger(FrmMovie.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmMovie.addBtnEnableChangesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });

        frmMovie.addBtnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }

            private void cancel() {
                frmMovie.dispose();
            }
        });

        frmMovie.addBtnDeleteActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }

            private void delete() {
                int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure?", 
                        "Delete movie", JOptionPane.YES_NO_OPTION);
                
                if (check == JOptionPane.YES_OPTION) {
                    Movie movie = makeMovieFromForm();
                    try {
                        Controller.getInstance().deleteMovie(movie);
                        JOptionPane.showMessageDialog(frmMovie, "Movie deleted successfully!\n", "Delete movie", JOptionPane.INFORMATION_MESSAGE);
                        frmMovie.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Delete movie", JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        frmMovie.addBtnUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

            private void update() {
                try {
                    validateForm();
                    int check = JOptionPane.showConfirmDialog(frmMovie, "Are you sure?", 
                        "Update movie", JOptionPane.YES_NO_OPTION);
                
                    if(check == JOptionPane.YES_OPTION){
                        
                        Movie movie = makeMovieFromForm();
                        Controller.getInstance().updateMovie(movie);
                        JOptionPane.showMessageDialog(frmMovie, "Movie updated successfully!\n", "Update movie", JOptionPane.INFORMATION_MESSAGE);
                        frmMovie.dispose();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
                
            }
        });
        frmMovie.addBtnAddActorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Role role = new Role() {
                    {
                        setRoleName(frmMovie.getTxtRoleName().getText().trim());
                        setActor((Actor)frmMovie.getCbActors().getSelectedItem());
                    }
                };
                
                roles.add(role);
                fillTblRoles(roles);
            }
        });
        frmMovie.addBtnRemoveActorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selRow = frmMovie.getTblRoles().getSelectedRow();
                Role role = ((RoleTableModel)frmMovie.getTblRoles().getModel()).getRoleAt(selRow);
                
                roles.remove(role);
                fillTblRoles(roles);
            }
        });
    }
    
    private void addListSelectionListener() {
        frmMovie.getTableRolesAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmMovie.getTblRoles().getSelectedRow() != -1)
                    frmMovie.getBtnRemoveActor().setEnabled(true);
                else
                    frmMovie.getBtnRemoveActor().setEnabled(false);
            }
        });
    }

    public void openForm(FormMode formMode) {
        frmMovie.setTitle("Movie form");
        prepareView(formMode);
        frmMovie.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmMovie.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        fillCbActors();
        fillCbGenres();
        fillCbProductionCompanies();
        fillCbDirector();
        setupComponents(formMode);
    }
    
    private void fillCbDirector() {
        try {
            frmMovie.getCbDirector().removeAllItems();
            List<Director> directors = Controller.getInstance().selectAllDirectors();
            frmMovie.getCbDirector().setModel(new DefaultComboBoxModel<>(directors.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading components", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmMovie.getTxtMovieID().setText("auto");
                frmMovie.getTxtScore().setText(String.valueOf(0));
                
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(false);
                frmMovie.getBtnUpdate().setEnabled(false);
                frmMovie.getBtnEnableChanges().setEnabled(false);
                frmMovie.getBtnAdd().setEnabled(true);

                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(true);
                frmMovie.getTxtDescription().setEditable(true);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(true);
                frmMovie.getReleaseDate().setEnabled(true);
                
                break;
            case FORM_VIEW:
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(true);
                frmMovie.getBtnUpdate().setEnabled(false);
                frmMovie.getBtnEnableChanges().setEnabled(true);
                frmMovie.getBtnAdd().setEnabled(false);
                
                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(false);
                frmMovie.getTxtDescription().setEditable(false);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(false);
                frmMovie.getReleaseDate().setEnabled(false);

                Movie movie = (Movie) MainCoordinator.getInstance().getParam(Constants.PARAM_MOVIE);
                frmMovie.getTxtMovieID().setText(movie.getMovieID() + "");
                frmMovie.getTxtName().setText(movie.getName());
                frmMovie.getTxtDescription().setText(movie.getDescription());
                frmMovie.getTxtScore().setText(String.valueOf(movie.getScore()));
                frmMovie.getReleaseDate().setDate(movie.getReleaseDate());
                frmMovie.getCbDirector().setSelectedItem(movie.getDirector());
                break;
            case FORM_EDIT:
                frmMovie.getBtnCancel().setEnabled(true);
                frmMovie.getBtnDelete().setEnabled(false);
                frmMovie.getBtnUpdate().setEnabled(true);
                frmMovie.getBtnEnableChanges().setEnabled(false);
                frmMovie.getBtnAdd().setEnabled(false);

                frmMovie.getTxtMovieID().setEditable(false);
                frmMovie.getTxtName().setEditable(true);
                frmMovie.getTxtDescription().setEditable(true);
                frmMovie.getTxtScore().setEditable(false);
                frmMovie.getCbDirector().setEnabled(true);
                frmMovie.getReleaseDate().setEnabled(true);
                break;
        }
    }

    private Movie makeMovieFromForm() {
        Movie movie = new Movie(){
            {
                setMovieID(Integer.parseInt(frmMovie.getTxtMovieID().getText().trim()));
                setName(frmMovie.getTxtName().getText().trim());
                setDescription(frmMovie.getTxtDescription().getText().trim());
                setScore(Double.parseDouble(frmMovie.getTxtScore().getText()));
                setDirector((Director) frmMovie.getCbDirector().getSelectedItem());
                setReleaseDate(frmMovie.getReleaseDate().getDate());
            }
        };
        
        return movie;
    }
    
    public void validateForm() throws Exception{
        if (frmMovie.getTxtName().getText().trim().isEmpty() || frmMovie.getTxtName() == null 
                || frmMovie.getReleaseDate().getDate() == null) {
            throw new Exception("Invalid input");
        }
    }

    private void fillCbActors() {
        try {
            frmMovie.getCbActors().removeAllItems();
            List<Actor> actors = Controller.getInstance().selectAllActors();
            frmMovie.getCbActors().setModel(new DefaultComboBoxModel<>(actors.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmMovie, "Error loading components", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void fillCbGenres() {

    }

    private void fillCbProductionCompanies() {

    }
    
    private void fillTblRoles(List<Role> roles) {
        
        try {
            RoleTableModel rtm = new RoleTableModel(roles);
            frmMovie.getTblRoles().setModel(rtm);
            
            setUpRoleTableColumns();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmMovie, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUpRoleTableColumns() throws Exception {
        TableColumnModel tcm = frmMovie.getTblRoles().getColumnModel();
        
        frmMovie.getTblRoles().setAutoCreateRowSorter(true);
        frmMovie.getTblRoles().getTableHeader().setResizingAllowed(false);

        frmMovie.getTblRoles().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
    }
}
