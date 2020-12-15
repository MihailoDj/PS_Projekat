/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.coordinator;

import java.util.HashMap;
import java.util.Map;
import view.controller.AccountSettingsController;
import view.controller.ActorController;
import view.controller.DirectorController;
import view.controller.LoginController;
import view.controller.MainController;
import view.controller.MovieCollectionController;
import view.controller.MovieController;
import view.controller.RegisterController;
import view.controller.ReviewController;
import view.controller.UserMainController;
import view.controller.UserMovieController;
import view.controller.ViewAllActorsController;
import view.controller.ViewAllDirectorsController;
import view.controller.ViewAllMoviesController;
import view.controller.ViewAllReviewsController;
import view.form.FrmAccountSettings;
import view.form.FrmActor;
import view.form.FrmDirector;
import view.form.FrmLogin;
import view.form.FrmMain;
import view.form.FrmMovie;
import view.form.FrmMovieCollection;
import view.form.FrmRegister;
import view.form.FrmReview;
import view.form.FrmUserMain;
import view.form.FrmUserMovie;
import view.form.FrmViewActors;
import view.form.FrmViewDirectors;
import view.form.FrmViewMovies;
import view.form.FrmViewReviews;
import view.form.util.FormMode;

/**
 *
 * @author Mihailo
 */
public class MainCoordinator {
    private static MainCoordinator instance;
    private final MainController mainController;
    private final UserMainController userMainController;
    private final Map<String, Object> params;
    
    private MainCoordinator() {
        mainController = new MainController(new FrmMain());
        userMainController = new UserMainController(new FrmUserMain());
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
    
    public void openRegisterForm() {
        RegisterController registerController = new RegisterController(new FrmRegister());
        registerController.openForm();
    }
    
    public void openMainForm() {
        mainController.openForm();
    }
    
    public void openUserMainForm() {
        userMainController.openForm();
    }
    
    public void openAccountSettingsForm() {
        AccountSettingsController asc = new AccountSettingsController(
                new FrmAccountSettings(userMainController.getFrmUserMain(), true));
        asc.openForm();
    }
    
    public void openViewAllReviewsForm() {
        ViewAllReviewsController cont = new ViewAllReviewsController(
                new FrmViewReviews(MainCoordinator.getInstance().getUserMainController().getFrmUserMain(), true));
        cont.openForm();
    }
    
    public void openReviewForm() {
        ReviewController reviewController = new ReviewController(new FrmReview(null, true));
        reviewController.openForm(FormMode.FORM_ADD);
    }
    
    public void openReviewEditForm() {
        ReviewController reviewController = new ReviewController(new FrmReview(null, true));
        reviewController.openForm(FormMode.FORM_EDIT);
    }
    
    public void openMovieCollectionForm() {
        MovieCollectionController mcc = new MovieCollectionController(
                new FrmMovieCollection(userMainController.getFrmUserMain(), true));
        mcc.openForm();
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
    
    public void openUserMovieForm() {
        UserMovieController cont = new UserMovieController(new FrmUserMovie(null, true));
        cont.openForm();
    }
    
    public void openDirectorForm() {
        DirectorController directorControlled = new DirectorController(new FrmDirector(mainController.getFrmMain(), true));
        directorControlled.openForm(FormMode.FORM_ADD);
    }
    
    public void openViewAllDirectorsForm() {
        FrmViewDirectors form = new FrmViewDirectors(mainController.getFrmMain(), true);
        
        ViewAllDirectorsController viewAllDirectorsController = new ViewAllDirectorsController(form);
        viewAllDirectorsController.openForm();
    }
    
    public void openDirectorDetailsForm() {
        FrmDirector directorDetails = new FrmDirector(mainController.getFrmMain(), true);
        DirectorController directorController = new DirectorController(directorDetails);
        directorController.openForm(FormMode.FORM_VIEW);
    }
    
    public void openActorForm() {
        ActorController actorController = new ActorController(new FrmActor(mainController.getFrmMain(), true));
        actorController.openForm(FormMode.FORM_ADD);
    }
    
    public void openViewAllActorsForm() {
        FrmViewActors form = new FrmViewActors(mainController.getFrmMain(), true);
        
        ViewAllActorsController viewAllActorsController = new ViewAllActorsController(form);
        viewAllActorsController.openForm();
    }
    
    public void openActorDetailsForm() {
        FrmActor actorDetails = new FrmActor(mainController.getFrmMain(), true);
        ActorController actorController = new ActorController(actorDetails);
        actorController.openForm(FormMode.FORM_VIEW);
    }
    
    public MainController getMainContoller() {
        return mainController;
    }
    
    public UserMainController getUserMainController() {
        return userMainController;
    }


    public void addParam(String name, Object key) {
        params.put(name, key);
    }

    public Object getParam(String name) {
        return params.get(name);
    }
}
