/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Director;
import domain.Movie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Mihailo
 */
public class DbMovieRepository implements DbRepository<Movie>{

    @Override
    public List<Movie> getAll() throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            List<Movie> movies = new ArrayList<Movie>();
            
            String sql = 
                    "SELECT * FROM movie m JOIN director d on (m.directorID = d.directorID)";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Director director = new Director(){
                    {
                        setDirectorID(rs.getInt("directorID"));
                        setFirstName(rs.getString("firstname"));
                        setLastName(rs.getString("lastname"));
                        setDateOfBirth(new GregorianCalendar());
                    }
                };
                
                Movie movie = new Movie(){
                    {
                        setMovieID(rs.getInt("movieID"));
                        setName(rs.getString("name"));
                        setReleaseDate(new GregorianCalendar());
                        setDescription(rs.getString("description"));
                        setScore(rs.getDouble("score"));
                        setDirector(director);
                    }
                };
                
                movies.add(movie);
            }
            
            rs.close();
            return movies;
        } catch (SQLException ex) {
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Connection error!");
        }
    }

    @Override
    public void add(Movie movie) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "INSERT INTO movie VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, movie.getMovieID());
            statement.setString(2, movie.getName());
            statement.setDate(3, (java.sql.Date) new Date());
            statement.setString(4, movie.getDescription());
            statement.setDouble(5, movie.getScore());
            statement.setInt(6, movie.getDirector().getDirectorID());
            
            statement.executeUpdate();
            statement.close();
            
        } catch(SQLException e) {
            throw new Exception("Error!");
        }
        
    }
    
}
