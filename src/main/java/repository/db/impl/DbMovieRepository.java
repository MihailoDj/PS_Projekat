/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Director;
import domain.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<Movie> selectAll() throws Exception {
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
                        setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
                    }
                };
                
                Movie movie = new Movie(){
                    {
                        setMovieID(rs.getInt("movieID"));
                        setName(rs.getString("name"));
                        setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                        setDescription(rs.getString("description"));
                        setScore(rs.getDouble("score"));
                        setDirector(director);
                    }
                };
                
                movies.add(movie);
            }
            
            statement.close();
            rs.close();
            
            return movies;
        } catch (SQLException ex) {
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Connection error!");
        }
    }

    @Override
    public void insert(Movie movie) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            
            String sql = "INSERT INTO movie VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, movie.getMovieID());
            statement.setString(2, movie.getName());
            statement.setObject(3, movie.getReleaseDate(), java.sql.Types.DATE);
            statement.setDouble(4, movie.getScore());
            statement.setString(5, movie.getDescription());
            statement.setInt(6, movie.getDirector().getDirectorID());
            
            statement.executeUpdate();
            
            statement.close();
        } catch(SQLException e) {
            throw new Exception("Error!");
        }
        
    }

    @Override
    public void delete(Movie movie) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String sql = "DELETE FROM movie WHERE movieID=" + movie.getMovieID();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
        
        statement.close();
    }

    @Override
    public void update(Movie movie) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "UPDATE movie SET movieID=?, name=?, releasedate=?, score=?, description=?, directorID=? "
                    + "WHERE movieID=" + movie.getMovieID();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, movie.getMovieID());
            statement.setString(2, movie.getName());
            statement.setObject(3, movie.getReleaseDate(), java.sql.Types.DATE);
            statement.setDouble(4, movie.getScore());
            statement.setString(5, movie.getDescription());
            statement.setInt(6, movie.getDirector().getDirectorID());
            
            statement.executeUpdate();
        
        } catch(Exception ex) {
            throw new Exception("Error!");
        }
    }

    @Override
    public Movie select(Movie obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
