/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Actor;
import java.util.List;
import domain.Movie;
import domain.User;
import domain.Director;
import repository.Repository;
import repository.db.DbRepository;
import repository.db.impl.DbActorRepository;
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
    private final Repository actorRepository;
    private static Controller controller;

    private Controller() {
        userRepository = new DbUserRepository();
        directorRepository = new DbDirectorRepository();
        movieRepository = new DbMovieRepository();
        actorRepository = new DbActorRepository();
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
                return user;
            } else {
                throw new Exception("Incorrect password.");
            }
        }
        
        throw new Exception("User doesn't exist.");
    }
    
    public List<Director> selectAllDirectors() throws Exception{
        List<Director> directors = null;
        
        try {
            directors = directorRepository.selectAll();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return directors;
    }
    
    public List<Movie> selectAllMovies() throws Exception {
        List<Movie> movies = null;
        
        try{
            movies = movieRepository.selectAll();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        
        return movies;
    }
    
    public List<Actor> selectAllActors() throws Exception{
        List<Actor> actors = null;
        
        try {
            actors = actorRepository.selectAll();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return actors;
    }
    
    public void insertMovie(Movie movie) throws Exception {
        ((DbRepository)movieRepository).connect();
        
        try{
            movieRepository.insert(movie);
            ((DbRepository)movieRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)movieRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)movieRepository).disconnect();
        }
    }
    
    public void deleteMovie(Movie movie) throws Exception {
        ((DbRepository)movieRepository).connect();
        
        try{
            movieRepository.delete(movie);
            ((DbRepository)movieRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)movieRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)movieRepository).disconnect();
        }
    }

    public void updateMovie(Movie movie) throws Exception {
        ((DbRepository)movieRepository).connect();
        
        try{
            ((DbRepository)movieRepository).update(movie);
            ((DbRepository)movieRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)movieRepository).rollback();
            throw e;
        }
    }
    
    public void insertDirector(Director director) throws Exception {
        ((DbRepository)directorRepository).connect();
        
        try{
            directorRepository.insert(director);
            ((DbRepository)directorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)directorRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)directorRepository).disconnect();
        }
    }
    
    public void deleteDirector(Director director) throws Exception {
        ((DbRepository)directorRepository).connect();
        
        try{
            directorRepository.delete(director);
            ((DbRepository)directorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)directorRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)directorRepository).disconnect();
        }
    }

    public void updateDirector(Director director) throws Exception {
        ((DbRepository)directorRepository).connect();
        
        try{
            ((DbRepository)directorRepository).update(director);
            ((DbRepository)directorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)directorRepository).rollback();
            throw e;
        }
    }
    
    public void insertActor(Actor actor) throws Exception {
        ((DbRepository)actorRepository).connect();
        
        try{
            actorRepository.insert(actor);
            ((DbRepository)actorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)actorRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)actorRepository).disconnect();
        }
    }
    
    public void deleteActor(Actor actor) throws Exception {
        ((DbRepository)actorRepository).connect();
        
        try{
            actorRepository.delete(actor);
            ((DbRepository)actorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)actorRepository).rollback();
            throw e;
        } finally {
            ((DbRepository)actorRepository).disconnect();
        }
    }

    public void updateActor(Actor actor) throws Exception {
        ((DbRepository)actorRepository).connect();
        
        try{
            ((DbRepository)actorRepository).update(actor);
            ((DbRepository)actorRepository).commit();
        }catch(Exception e){
            e.printStackTrace();
            ((DbRepository)actorRepository).rollback();
            throw e;
        }
    }
}
