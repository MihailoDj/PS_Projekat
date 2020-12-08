/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Director;
import domain.Movie;
import domain.MoviePoster;
import domain.User;
import domain.UserMovieCollection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Mihailo
 */
public class DbUserMovieCollectionRepository implements DbRepository<UserMovieCollection>{

    @Override
    public void insert(UserMovieCollection collection) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "INSERT INTO collection (movieID, userID) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, collection.getMovie().getMovieID());
            statement.setInt(2, collection.getUser().getUserID());
            statement.executeUpdate();
            
            statement.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to save movie.");
        }
    }

    @Override
    public void delete(UserMovieCollection umc) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "DELETE FROM collection WHERE movieID=" + umc.getMovie().getMovieID() + " AND "
                    + "userID=" + umc.getUser().getUserID();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to delete saved movie");
        }
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(UserMovieCollection obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserMovieCollection> select(String criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserMovieCollection> selectAll() throws Exception {
        try {
            List<UserMovieCollection> collection = new ArrayList<>();
            
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "SELECT * FROM collection c JOIN movie m ON (c.movieID=m.movieID) "
                    + "JOIN user u ON (u.userID=c.userID) JOIN director d ON (d.directorID=m.directorID) "
                    + "JOIN movieposter mp ON (mp.movieposterID=m.movieposterID)";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while (rs.next()) {
                UserMovieCollection col = new UserMovieCollection() {
                    {
                        setMovie(new Movie() {
                            {
                                setMovieID(rs.getInt("movieID"));
                                setName(rs.getString("name"));
                                setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                                setDescription(rs.getString("description"));
                                setScore(rs.getDouble("score"));
                                setDirector(new Director() {
                                    {
                                        setDirectorID(rs.getInt("directorID"));
                                        setFirstName(rs.getString("firstname"));
                                        setLastName(rs.getString("lastname"));
                                        setDateOfBirth(rs.getObject("dateofbirth", LocalDate.class));
                                    }
                                });
                                setMoviePoster(new MoviePoster() {
                                    {
                                        setMoviePosterID(rs.getInt("movieposterID"));
                                        setPosterImage(ImageIO.read(rs.getBlob("posterimage").getBinaryStream()));
                                    }
                                });
                            }
                        });
                        setUser(new User() {
                            {
                                setUserID(rs.getInt("userID"));
                                setUsername(rs.getString("username"));
                                setPassword(rs.getString("password"));
                                setAdmin(rs.getBoolean("admin"));
                            }
                        });
                    }
                };
                
                collection.add(col);
            }
            
            statement.close();
            rs.close();
            
            return collection;
        } catch(Exception e) {
            e.printStackTrace();
            throw new Exception("Error loading movie collection!");
        }
    }
    
}
