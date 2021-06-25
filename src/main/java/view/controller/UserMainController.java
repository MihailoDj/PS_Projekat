/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import comm.Operation;
import comm.Request;
import communication.Communication;
import domain.Director;
import domain.Movie;
import domain.MoviePoster;
import domain.User;
import domain.UserMovieCollection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import util.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmUserMain;
import component.MovieTableModel;

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
        frmUserMain.getLblCurrentUser().setText("Current user: " + user.getUsername());
        
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

            @Override
            public void windowClosing(WindowEvent e) {
                logout();
            }
            
            
            
        });
        frmUserMain.jmiAccountSettings(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAccountSettingsForm();
            }
        });
        frmUserMain.jmiLogoutActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        frmUserMain.jmiViewMyMoviesActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openMovieCollectionForm();
            }
        });
        frmUserMain.jmiReviews(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openViewAllReviewsForm();
            }
        });
        frmUserMain.btnSearchActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validateSearchField();
                    
                    String searchParam = frmUserMain.getTxtSearch().getText().trim();
                    
                    Director director = new Director();
                    director.setFirstName(searchParam);
                    director.setLastName(searchParam);
                    
                    MoviePoster mp = new MoviePoster();
                    mp.setPosterImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
                    
                    Movie movie = new Movie();
                    movie.setName(searchParam);
                    movie.setDirector(director);
                    movie.setMoviePoster(mp);
                    
                    Request request = new Request(Operation.SELECT_MOVIES, movie);
                    Communication.getInstance().sendUserRequest(request);
                    
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
                    
                    UserMovieCollection collection = new UserMovieCollection();
                    collection.setMovie(movie);
                    collection.setUser(user);
                    
                    Request request = new Request(Operation.INSERT_COLLECTION, collection);
                    Communication.getInstance().sendUserRequest(request);
                    
                } catch (Exception ex) {
                    Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmUserMain, "Unable to save movie to collection!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    public void fillTblMovies(List<Movie> movies) {
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
        TableColumnModel tcm = frmUserMain.getTblMovies().getColumnModel();
        
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
    
    private void logout() {
        try {
            User currentUser = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
            currentUser.setStatus("offline");
            Request request = new Request(Operation.UPDATE_USER, currentUser);
            Communication.getInstance().sendUserRequest(request);
            
            Communication.stopCommunication();

            MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, null);
            frmUserMain.dispose();
        } catch (Exception ex) {
            Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public FrmUserMain getFrmUserMain() {
        return frmUserMain;
    }
}
