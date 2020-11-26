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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmViewMovies;
import view.form.component.table.MovieTableModel;

/**
 *
 * @author Mihailo
 */
public class ViewAllMoviesController {
    private final FrmViewMovies frmViewMovies;

    public ViewAllMoviesController(FrmViewMovies frmViewMovies) {
        this.frmViewMovies = frmViewMovies;
        addActionListener();
    }

    private void addActionListener() {
        frmViewMovies.getBtnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewMovies.getTblMovies().getSelectedRow();
                
                if (row != -1) {
                    Movie movie = ((MovieTableModel) frmViewMovies.getTblMovies().getModel()).getMovieAt(row);
                    MainCoordinator.getInstance().addParam(Constants.PARAM_MOVIE, movie);
                    MainCoordinator.getInstance().openMovieDetailsForm();
                } else {
                    JOptionPane.showMessageDialog(frmViewMovies, "You must select a movie", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmViewMovies.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblMovies();
            }
            
        });
        
        frmViewMovies.getBtnAddMovieAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openMovieForm();
            }
        });
        
        frmViewMovies.getBtnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewMovies.getTblMovies().getSelectedRow();
                
                if (row != -1) {
                    try {
                        int check = JOptionPane.showConfirmDialog(frmViewMovies, "Are you sure?", 
                                "Delete movie", JOptionPane.YES_NO_OPTION);
                        
                        if (check == JOptionPane.YES_OPTION) {
                            Movie movie = ((MovieTableModel) frmViewMovies.getTblMovies().getModel()).getMovieAt(row);
                            MainCoordinator.getInstance().addParam(Constants.PARAM_MOVIE, null);
                            Controller.getInstance().deleteMovie(movie);
                            JOptionPane.showMessageDialog(frmViewMovies, "Movie successfully deleted", 
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                    } catch (Exception ex) {
                        Logger.getLogger(ViewAllMoviesController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(frmViewMovies, "Unable to delete movie", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frmViewMovies, "You must select a movie", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void openForm() {
        prepareView();
        frmViewMovies.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmViewMovies.setVisible(true);
    }

    private void prepareView() {
        frmViewMovies.setTitle("View movies");
    }

    private void fillTblMovies() {
        List<Movie> movies;
        
        
        try {
            movies = Controller.getInstance().selectAllMovies();
            MovieTableModel mtm = new MovieTableModel(movies);
            frmViewMovies.getTblMovies().setModel(mtm);
            
            setUpTableColumns();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewMovies, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ViewAllMoviesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUpTableColumns() throws Exception {
        List<Director> directors = directors = Controller.getInstance().selectAllDirectors();
        JComboBox cbDirector = new JComboBox(directors.toArray());

        TableColumnModel tcm = frmViewMovies.getTblMovies().getColumnModel();
        TableColumn tcDirector = tcm.getColumn(5);
        tcDirector.setCellEditor(new DefaultCellEditor(cbDirector));

        TableColumn tcReleaseDate = tcm.getColumn(2);
        tcReleaseDate.setCellEditor(new DateTableEditor());

        frmViewMovies.getTblMovies().setAutoCreateRowSorter(true);
        frmViewMovies.getTblMovies().getTableHeader().setResizingAllowed(false);

        frmViewMovies.getTblMovies().setRowHeight(30);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(75);
        tcm.getColumn(4).setPreferredWidth(15);
        tcm.getColumn(5).setPreferredWidth(100);
    }
}
