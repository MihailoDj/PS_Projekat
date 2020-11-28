/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.coordinator;

import java.util.HashMap;
import java.util.Map;
import view.controller.DirectorController;
import view.controller.LoginController;
import view.controller.MainController;
import view.controller.MovieController;
import view.controller.ViewAllMoviesController;
import view.form.FrmDirector;
import view.form.FrmLogin;
import view.form.FrmMain;
import view.form.FrmMovie;
import view.form.FrmViewMovies;
import view.form.util.FormMode;

/**
 *
 * @author Mihailo
 */
public class MainCoordinator {
    private static MainCoordinator instance;
    private final MainController mainController;
    private final Map<String, Object> params;
    
    private MainCoordinator() {
        mainController = new MainController(new FrmMain());
        params = new HashMap<>();
    }
    
    public static MainCoordinator getInstance() {
        if (instance == null) {
            instance = new MainCoordinator();
        }
        return instance;
    }
    
    public void openLoginForm() {
        LoginController loginContoller = new LoginController(new FrmLogin());
        loginContoller.openForm();
    }
    
    public void openMainForm() {
        mainController.openForm();
    }
    
    public void openMovieForm() {
        MovieController movieController = new MovieController(new FrmMovie(mainController.getFrmMain(), true));
        movieController.openForm(FormMode.FORM_ADD);
    }

    public void openViewAllMoviesForm() {
        FrmViewMovies form = new FrmViewMovies(mainController.getFrmMain(), true);
        
        ViewAllMoviesController viewAllMoviesController = new ViewAllMoviesController(form);
        viewAllMoviesController.openForm();
    }
    
    public void openMovieDetailsForm() {
        FrmMovie movieDetails = new FrmMovie(mainController.getFrmMain(), true);
        MovieController movieController = new MovieController(movieDetails);
        movieController.openForm(FormMode.FORM_VIEW);
    }
    
    public void openDirectorForm() {
        DirectorController directorControlled = new DirectorController(new FrmDirector(mainController.getFrmMain(), true));
        directorControlled.openForm(FormMode.FORM_ADD);
    }
    
    public MainController getMainContoller() {
        return mainController;
    }


    public void addParam(String name, Object key) {
        params.put(name, key);
    }

    public Object getParam(String name) {
        return params.get(name);
    }
}
