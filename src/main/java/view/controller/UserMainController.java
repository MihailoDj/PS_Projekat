/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import controller.Controller;
import domain.Director;
import domain.Movie;
import domain.User;
import domain.UserMovieCollection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmUserMain;
import view.form.component.table.MovieTableModel;

/**
 *
 * @author Mihailo
 */
public class UserMainController {
    private final FrmUserMain frmUserMain;

    public UserMainController(FrmUserMain frmUserMain) {
        this.frmUserMain = frmUserMain;
        addActionListener();
        addListSelectionListener();
    }

    public void openForm() {
        frmUserMain.setTitle("Homepage");
        frmUserMain.setLocationRelativeTo(null);
        
        User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        String systemRole = (user.isAdmin()) ? "administrator" : "user";
        frmUserMain.getLblCurrentUser().setText("Current " + systemRole + ": " + user.getUsername());
        
        frmUserMain.setVisible(true);
    }
    
    private void addListSelectionListener() {
        frmUserMain.tblMoviesAddListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmUserMain.getTblMovies().getSelectedRow() != -1)
                    frmUserMain.getBtnSave().setEnabled(true);
                else
                    frmUserMain.getBtnSave().setEnabled(false);
            }
        });
    }
    
    private void addActionListener() {
        frmUserMain.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblMovies(new ArrayList<>());
            }
        });
        frmUserMain.jmiAccountSettings(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAccountSettingsForm();
            }
        });
        frmUserMain.jmiViewMyMoviesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openViewAllMoviesForm();
            }
        });
        frmUserMain.btnSearchActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validateSearchField();
                    
                    String searchParam = frmUserMain.getTxtSearch().getText().trim();
                    List<Movie> movies = Controller.getInstance().selectMovies(searchParam);
                    fillTblMovies(movies);
                } catch (Exception ex) {
                    Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmUserMain, ex.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmUserMain.btnSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = frmUserMain.getTblMovies().getSelectedRow();
                    Movie movie = ((MovieTableModel)frmUserMain.getTblMovies().getModel()).getMovieAt(selectedRow);
                    User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                    
                    UserMovieCollection collection = new UserMovieCollection(){
                        {
                            setMovie(movie);
                            setUser(user);
                        }
                    };
                    Controller.getInstance().insertCollection(collection);
                    JOptionPane.showMessageDialog(frmUserMain, "Movie successfully saved to collection!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                
                } catch (Exception ex) {
                    Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmUserMain, "Unable to save movie to collection!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    private void fillTblMovies(List<Movie> movies) {
        try {
            MovieTableModel mtm = new MovieTableModel(movies);
            frmUserMain.getTblMovies().setModel(mtm);
            
            setUpTableColumns();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmUserMain, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUpTableColumns() throws Exception {
        List<Director> directors = Controller.getInstance().selectAllDirectors();
        JComboBox cbDirector = new JComboBox(directors.toArray());

        TableColumnModel tcm = frmUserMain.getTblMovies().getColumnModel();
        TableColumn tcDirector = tcm.getColumn(5);
        tcDirector.setCellEditor(new DefaultCellEditor(cbDirector));

        TableColumn tcReleaseDate = tcm.getColumn(2);
        tcReleaseDate.setCellEditor(new DateTableEditor());
        tcReleaseDate.setCellRenderer(new DateTableEditor());

        frmUserMain.getTblMovies().setAutoCreateRowSorter(true);
        frmUserMain.getTblMovies().getTableHeader().setResizingAllowed(false);

        frmUserMain.getTblMovies().setRowHeight(30);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(75);
        tcm.getColumn(4).setPreferredWidth(15);
        tcm.getColumn(5).setPreferredWidth(100);
    }
    
    private void validateSearchField() throws Exception{
        if(frmUserMain.getTxtSearch().getText().trim().isEmpty()) {
            throw new Exception("Search field can't be empty!");
        }
    }
    
    public FrmUserMain getFrmUserMain() {
        return frmUserMain;
    }
}
