/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.UserMovieCollection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
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
    public void delete(UserMovieCollection obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
