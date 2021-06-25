/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import comm.Response;
import communication.Communication;
import domain.Movie;
import domain.Review;
import domain.User;
import domain.UserMovieCollection;
import java.util.List;
import javax.swing.JOptionPane;
import util.Constants;
import view.coordinator.MainCoordinator;

/**
 *
 * @author Mihailo
 */
public class ServerResponseHandler extends Thread{

    @Override
    public void run() {
        try {
            while (true) {            
                Response response = Communication.getInstance().receiveServerResponse();
                switch(response.getOperation()){
                    case LOGIN:
                        User userLogin = (User)response.getResult();
                        
                        if (userLogin != null) {
                            MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, userLogin);
                            JOptionPane.showMessageDialog(
                                MainCoordinator.getInstance().getLoginController().getFrmLogin(), "Welcome " + userLogin.toString(), "Login successful", JOptionPane.INFORMATION_MESSAGE);
                            
                            MainCoordinator.getInstance().getLoginController().getFrmLogin().dispose();
                            MainCoordinator.getInstance().openUserMainForm();
                        } else {
                            JOptionPane.showMessageDialog(
                                MainCoordinator.getInstance().getLoginController().getFrmLogin(), "Invalid credentials", "Login failed", JOptionPane.ERROR_MESSAGE);
                        
                        }
                        break;
                    case INSERT_USER:
                        if (response.getException() != null) {
                            JOptionPane.showMessageDialog(MainCoordinator.getInstance().getRegisterController().getFrmRegister(), "Unable to register account!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainCoordinator.getInstance().getRegisterController().getFrmRegister(), "Account registration successful", "Register", 
                            JOptionPane.INFORMATION_MESSAGE);
                            MainCoordinator.getInstance().getRegisterController().getFrmRegister().dispose();
                        }
                        break;
                    case INSERT_COLLECTION:
                        if (response.getException() != null) {
                            JOptionPane.showMessageDialog(MainCoordinator.getInstance().getUserMainController().getFrmUserMain(), "Unable to save movie!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainCoordinator.getInstance().getUserMainController().getFrmUserMain(), "Movie added to collection", "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case SELECT_COLLECTIONS:
                        List<UserMovieCollection> collections = (List<UserMovieCollection>) response.getResult();
                        MainCoordinator.getInstance().getMovieCollectionController().fillTblCollection(collections);
                        break;
                    case SELECT_REVIEWS:
                        List<Review> reviews = (List<Review>) response.getResult();
                        if (MainCoordinator.getInstance().getMovieCollectionController() != null){
                            MainCoordinator.getInstance().getMovieCollectionController().fillTblReviews(reviews);
                        } else {
                            MainCoordinator.getInstance().getViewAllReviewsController().fillTblReviews(reviews);
                        }
                        break;
                    case SELECT_MOVIES:
                        List<Movie> movies = (List<Movie>) response.getResult();
                        MainCoordinator.getInstance().getUserMainController().fillTblMovies(movies);
                        break;
                    case SELECT_ALL_MOVIES:
                        List<Movie> allMovies = (List<Movie>) response.getResult();
                        MainCoordinator.getInstance().getUserMainController().fillTblMovies(allMovies);
                        break;
                    case LOGOUT_ALL:
                        JOptionPane.showMessageDialog(MainCoordinator.getInstance().getUserMainController().getFrmUserMain(), "Server is closed");
                        Communication.stopCommunication();
                        MainCoordinator.getInstance().getUserMainController().getFrmUserMain().dispose();
                        break;
                }

            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    
}
