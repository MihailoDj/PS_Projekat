/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import domain.Movie;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.table.TableColumnModel;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmUserMovie;
import view.form.component.table.MovieGenreTableModel;
import view.form.component.table.ProductionTableModel;
import view.form.component.table.RoleTableModel;

/**
 *
 * @author Mihailo
 */
public class UserMovieController {
    private final FrmUserMovie form;
    
    public UserMovieController(FrmUserMovie form) {
        this.form = form;   
    }
    
    public void openForm() {
        prepareView();
        form.setTitle("Movie details");
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }

    private void prepareView() {
        Movie movie = (Movie)MainCoordinator.getInstance().getParam(Constants.PARAM_MOVIE);
        
        form.getLblImagePoster().setIcon(new ImageIcon(
                        movie.getMoviePoster().getPosterImage().getScaledInstance(form.getLblImagePoster().getWidth(),
                                form.getLblImagePoster().getHeight(), Image.SCALE_SMOOTH)));
        
        form.getLblName().setText(movie.getName());
        form.getTxtDescription().setText(movie.getDescription());
        form.getLblScore().setText(String.valueOf(movie.getScore()));
        form.getLblReleaseDate().setText(movie.getReleaseDate() + "");
        form.getLblDirector().setText(movie.getDirector() + "");
        
        RoleTableModel rtm = new RoleTableModel(movie.getRoles());
        form.getTblRoles().setModel(rtm);
        form.getTblRoles().setEnabled(false);

        MovieGenreTableModel mgtm = new MovieGenreTableModel(movie.getMovieGenres());
        form.getTblMovieGenre().setModel(mgtm);
        form.getTblMovieGenre().setEnabled(false);

        ProductionTableModel ptm = new ProductionTableModel(movie.getProductions());
        form.getTblProduction().setModel(ptm);
        form.getTblProduction().setEnabled(false);
        
        setUpRoleTableColumns();
        setUpMovieGenreTableColumns();
        setUpProductionTableColumns();
    }
    
    private void setUpProductionTableColumns() {
        TableColumnModel tcm = form.getTblProduction().getColumnModel();
        
        form.getTblProduction().setAutoCreateRowSorter(true);
        form.getTblProduction().getTableHeader().setResizingAllowed(false);

        form.getTblProduction().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(300);
    }
    
    private void setUpRoleTableColumns(){
        TableColumnModel tcm = form.getTblRoles().getColumnModel();
        
        form.getTblRoles().setAutoCreateRowSorter(true);
        form.getTblRoles().getTableHeader().setResizingAllowed(false);

        form.getTblRoles().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
    }
    
    private void setUpMovieGenreTableColumns() {
        TableColumnModel tcm = form.getTblMovieGenre().getColumnModel();
        
        form.getTblMovieGenre().setAutoCreateRowSorter(true);
        form.getTblMovieGenre().getTableHeader().setResizingAllowed(false);

        form.getTblMovieGenre().setRowHeight(20);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(300);
    }
}
