/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.swing.JOptionPane;
import domain.Movie;
import domain.User;
import domain.Director;
import repository.MovieRepository;
import repository.UserRepository;
import repository.DirectorRepository;
import repository.Repository;
import repository.db.impl.DbDirectorRepository;
import repository.db.impl.DbMovieRepository;
import repository.db.impl.DbUserRepository;

/**
 *
 * @author Mihailo
 */
public class Controller {
    private final Repository userRepository;
    private final Repository directorRepository;
    private final Repository movieRepository;
    private static Controller controller;

    private Controller() {
        userRepository = new DbUserRepository();
        directorRepository = new DbDirectorRepository();
        movieRepository = new DbMovieRepository();
    }
    
    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller();
        
        return controller;
    }
    
    public User login(String username, String password) throws Exception{
        List<User> users = userRepository.getAll();
        
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                LoggedInUser.getInstance().setLoggedInUser(user);
                return user;
            }
        }
        
        throw new Exception("User doesn't exist.");
    }
    
    public List<Director> getAllDirectors() throws Exception {
        return directorRepository.getAll();
    }
    
    public void addMovie(Movie movie) throws Exception {
        movieRepository.add(movie);
    }
    
    public List<Movie> getAllMovies() throws Exception {
        return movieRepository.getAll();
    }
    
    public void removeMovie(Movie movie) {
        //movieRepository.removeMovie(movie);
    }

    public void updateMovie(Movie movie) {
        //movieRepository.updateMovie(movie);
    }
}
