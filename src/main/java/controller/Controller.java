/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import domain.Movie;
import domain.User;
import domain.Director;
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
        User user = (User)userRepository.select(new User(0, username, password, false));
        
        if (!user.equals(null)) {
            if(password.equals(user.getPassword())) {
                LoggedInUser.getInstance().setLoggedInUser(user);
                return user;
            } else {
                throw new Exception("Incorrect password.");
            }
        }
        
        throw new Exception("User doesn't exist.");
    }
    
    public List<Director> selectAllDirectors() throws Exception {
        return directorRepository.selectAll();
    }
    
    public void insertMovie(Movie movie) throws Exception {
        movieRepository.insert(movie);
    }
    
    public List<Movie> selectAllMovies() throws Exception {
        return movieRepository.selectAll();
    }
    
    public void deleteMovie(Movie movie) throws Exception {
        movieRepository.delete(movie);
    }

    public void updateMovie(Movie movie) throws Exception {
        movieRepository.update(movie);
    }
}
