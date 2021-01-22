/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.coordinator;

import java.util.HashMap;
import java.util.Map;
import view.controller.AccountSettingsController;
import view.controller.LoginController;
import view.controller.MovieCollectionController;
import view.controller.RegisterController;
import view.controller.ReviewController;
import view.controller.UserMainController;
import view.controller.UserMovieController;
import view.controller.ViewAllReviewsController;
import view.form.FrmAccountSettings;
import view.form.FrmLogin;
import view.form.FrmMovieCollection;
import view.form.FrmRegister;
import view.form.FrmReview;
import view.form.FrmUserMain;
import view.form.FrmUserMovie;
import view.form.FrmViewReviews;
import util.FormMode;

/**
 *
 * @author Mihailo
 */
public class MainCoordinator {
    private static MainCoordinator instance;
    private final UserMainController userMainController;
    private final Map<String, Object> params;
    
    private MainCoordinator() {
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
    
    public void openReviewDetailsForm() {
        ReviewController reviewController = new ReviewController(new FrmReview(null, true));
        reviewController.openForm(FormMode.FORM_VIEW);
    }
    
    public void openMovieCollectionForm() {
        MovieCollectionController mcc = new MovieCollectionController(
                new FrmMovieCollection(userMainController.getFrmUserMain(), true));
        mcc.openForm();
    }
    
    public void openUserMovieForm() {
        UserMovieController cont = new UserMovieController(new FrmUserMovie(null, true));
        cont.openForm();
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
