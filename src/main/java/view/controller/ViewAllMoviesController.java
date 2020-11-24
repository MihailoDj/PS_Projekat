/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import controller.Controller;
import domain.Movie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
                    JOptionPane.showMessageDialog(frmViewMovies, "You must select a movie", "MOVIE DETAILS", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void openForm() {
        frmViewMovies.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        prepareView();
        frmViewMovies.setVisible(true);
    }

    private void prepareView() {
        frmViewMovies.setTitle("View movies");
        fillTblMovies();
    }

    private void fillTblMovies() {
        List<Movie> movies;
        try {
            movies = Controller.getInstance().selectAllMovies();
            MovieTableModel mtm = new MovieTableModel(movies);
            frmViewMovies.getTblMovies().setModel(mtm);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewMovies, "Error: " + ex.getMessage(), "ERROR DETAILS", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ViewAllMoviesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() {
        fillTblMovies();
    }
}
