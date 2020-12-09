/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Movie;
import domain.Review;
import domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;
import view.constant.Constants;
import view.coordinator.MainCoordinator;

/**
 *
 * @author Mihailo
 */
public class DbReviewRepository implements DbRepository<Review>{

    @Override
    public void insert(Review review) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "INSERT INTO review (reviewID, reviewtext, reviewscore, reviewdate, movieID, userID)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            Timestamp timestamp = Timestamp.valueOf(review.getReviewDate());
            
            statement.setInt(1, review.getReviewID());
            statement.setString(2, review.getReviewText());
            statement.setInt(3, review.getReviewScore());
            statement.setTimestamp(4, timestamp);
            statement.setInt(5, review.getMovie().getMovieID());
            statement.setInt(6, review.getUser().getUserID());
            
            statement.executeUpdate();
            
            //UPDATE MOVIE WITH NEW AVERAGE SCORE
            sql = "UPDATE movie m SET score =(SELECT AVG(reviewscore) FROM review r WHERE r.movieID="
                    + review.getMovie().getMovieID() + ") WHERE m.movieID=" + review.getMovie().getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            
            statement.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void delete(Review obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Review obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Review> select(String criteria) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Review> selectAll() throws Exception {
        try {
            User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
            List<Review> reviews = new ArrayList<>();
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            String sql = "SELECT * FROM review r JOIN movie m ON (m.movieID=r.movieID) WHERE r.userID=" + user.getUserID();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                Review review = new Review() {
                    {
                        setReviewID(rs.getInt("reviewID"));
                        setReviewText(rs.getString("reviewtext"));
                        setReviewScore(rs.getInt("reviewscore"));
                        setReviewDate(rs.getTimestamp("reviewdate").toLocalDateTime());
                        setMovie(new Movie(){
                            {
                                setMovieID(rs.getInt("movieID"));
                                setName(rs.getString("name"));
                                setReleaseDate(rs.getObject("releaseDate", LocalDate.class));
                                setScore(rs.getDouble("score"));
                                setDescription(rs.getString("description"));
                            }
                        });
                        setUser(user);
                    }  
                };
                reviews.add(review);
            }
        
            statement.close();
            rs.close();
            return reviews;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
