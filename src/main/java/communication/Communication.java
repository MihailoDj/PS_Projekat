/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import comm.Operation;
import comm.Receiver;
import comm.Response;
import comm.Request;
import comm.Sender;
import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;
import domain.ProductionCompany;
import domain.Review;
import domain.User;
import domain.UserMovieCollection;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Mihailo
 */
public class Communication {
    private static Communication instance;
    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    
    private Communication() throws Exception{
        socket = new Socket("localhost", 4000);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }
    
    public static Communication getInstance() throws Exception{
        if (instance == null) 
            instance = new Communication();
        return instance;
    }
    
    public User login(String username, String password) throws Exception{
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        
        Request request = new Request(Operation.LOGIN, user);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if (response.getException() == null)
            return (User)response.getResult();
        else
            throw response.getException();
    }
    
    public List<User> selectUser(User user) throws Exception{
        Request request = new Request(Operation.SELECT_USER, user);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if (response.getException() == null)
            return (List<User>)response.getResult();
        else
            throw response.getException();
    }
    
    public void updateUser(User user) throws Exception{
        Request request = new Request(Operation.UPDATE_USER, user);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public void insertUser(User user) throws Exception{
        Request request = new Request(Operation.INSERT_USER, user);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
           
    }
    
    public void deleteUser(User user) throws Exception {
        Request request = new Request(Operation.DELETE_USER, user);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public List<Director> selectAllDirectors() throws Exception{
        Request request = new Request(Operation.SELECT_ALL_DIRECTORS, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() == null) 
            return (List<Director>)response.getResult();
        else
            throw response.getException();
    }
    
    public List<Movie> selectAllMovies() throws Exception {
        Request request = new Request(Operation.SELECT_ALL_MOVIES, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() == null) 
            return (List<Movie>)response.getResult();
        else
            throw response.getException();
    }
    
    public List<Actor> selectAllActors() throws Exception{
        Request request = new Request(Operation.SELECT_ALL_ACTORS, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() == null) 
            return (List<Actor>)response.getResult();
        else
            throw response.getException();
    }
    
    public List<Genre> selectAllGenres() throws Exception{
        Request request = new Request(Operation.SELECT_ALL_GENRES, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() == null) 
            return (List<Genre>)response.getResult();
        else
            throw response.getException();
    }
    
    public List<ProductionCompany> selectAllProductionCompanies() throws Exception{
        Request request = new Request(Operation.SELECT_ALL_PRODUCTION_COMPANIES, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() == null) 
            return (List<ProductionCompany>)response.getResult();
        else
            throw response.getException();
    }
    
    public void insertMovie(Movie movie) throws Exception {
        Request request = new Request(Operation.INSERT_MOVIE, movie);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() == null){
            Movie newMovie = (Movie)response.getResult();
            movie.setMovieID(newMovie.getMovieID());
        } else{
             throw response.getException();
        }
    }
    
    public void deleteMovie(Movie movie) throws Exception {
        Request request = new Request(Operation.DELETE_MOVIE, movie);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }

    public void updateMovie(Movie movie) throws Exception {
        Request request = new Request(Operation.UPDATE_MOVIE, movie);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public List<Movie> selectMovies(Movie movie) throws Exception {
        Request request = new Request(Operation.SELECT_MOVIES, movie);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if (response.getException() == null)
            return (List<Movie>)response.getResult();
        else
            throw response.getException();
    }
    
    public void insertDirector(Director director) throws Exception {
        Request request = new Request(Operation.INSERT_DIRECTOR, director);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public void deleteDirector(Director director) throws Exception {
        Request request = new Request(Operation.DELETE_DIRECTOR, director);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }

    public void updateDirector(Director director) throws Exception {
        Request request = new Request(Operation.UPDATE_DIRECTOR, director);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public void insertActor(Actor actor) throws Exception {
        Request request = new Request(Operation.INSERT_ACTOR, actor);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public void deleteActor(Actor actor) throws Exception {
        Request request = new Request(Operation.DELETE_ACTOR, actor);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }

    public void updateActor(Actor actor) throws Exception {
        Request request = new Request(Operation.UPDATE_ACTOR, actor);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public void insertCollection(UserMovieCollection collection) throws Exception{
        Request request = new Request(Operation.INSERT_COLLECTION, collection);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public List<UserMovieCollection> selectAllCollections() throws Exception{
        Request request = new Request(Operation.SELECT_ALL_COLLECTIONS, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() == null) 
            return (List<UserMovieCollection>)response.getResult();
        else
            throw response.getException();
    }
    
     public List<UserMovieCollection> selectCollections(UserMovieCollection collection) throws Exception {
        Request request = new Request(Operation.SELECT_COLLECTIONS, collection);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if (response.getException() == null)
            return (List<UserMovieCollection>)response.getResult();
        else
            throw response.getException();
    }
    
    public void deleteCollection(UserMovieCollection collection) throws Exception{
        Request request = new Request(Operation.DELETE_COLLECTION, collection);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public void insertReview(Review review) throws Exception{
        Request request = new Request(Operation.INSERT_REVIEW, review);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }

    public List<Review> selectAllReviews() throws Exception{
        Request request = new Request(Operation.SELECT_ALL_REVIEWS, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() == null) 
            return (List<Review>)response.getResult();
        else
            throw response.getException();
    }
    
    public List<Review> selectReviews(Review review) throws Exception {
        Request request = new Request(Operation.SELECT_REVIEWS, review);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if (response.getException() == null)
            return (List<Review>)response.getResult();
        else
            throw response.getException();
    }
    
    public void deleteReview(Review review) throws Exception{
        Request request = new Request(Operation.DELETE_REVIEW, review);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
    
    public void updateReview (Review review) throws Exception {
        Request request = new Request(Operation.UPDATE_REVIEW, review);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        if(response.getException() != null)
             throw response.getException();
    }
}
