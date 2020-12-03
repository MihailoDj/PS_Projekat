/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.Actor;
import domain.Director;
import domain.Genre;
import domain.Movie;
import domain.MovieGenre;
import domain.Production;
import domain.ProductionCompany;
import domain.Role;
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
            
            String sql = "SELECT * FROM movie m JOIN director d ON (m.directorID = d.directorID)";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()) {
                //LOAD MOVIE
                Movie movie = new Movie(){
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
                    }
                };
                
                //LOAD ROLES
                String sqlRoles = "SELECT * FROM movie m JOIN role r ON (m.movieID = r.movieID) "
                        + "JOIN actor a ON (r.actorID = a.actorID)"
                        + "WHERE r.movieID = " + movie.getMovieID();
                Statement statementRoles = connection.createStatement();
                ResultSet rsRoles = statementRoles.executeQuery(sqlRoles);
                
                while(rsRoles.next()) {
                    Role role = new Role() {
                        {
                            setRoleName(rsRoles.getString("rolename"));
                            setActor(new Actor() { {
                                setActorID(rsRoles.getInt("actorID"));
                                setFirstName(rsRoles.getString("firstname"));
                                setLastName(rsRoles.getString("lastname"));
                                setBiography(rsRoles.getString("biography"));
                            }});
                            setMovie(movie);
                        }  
                    };
                    movie.getRoles().add(role);
                }
                
                //LOAD MOVIE GENRES
                String sqlMovieGenres = "SELECT g.name as gname, g.genreID as ggenreID FROM movie m JOIN movie_genre mg ON (m.movieID = mg.movieID) "
                        + "JOIN genre g ON (mg.genreID = g.genreID)"
                        + "WHERE mg.movieID = " + movie.getMovieID();
                Statement statementMovieGenres = connection.createStatement();
                ResultSet rsMovieGenres = statementMovieGenres.executeQuery(sqlMovieGenres);
                
                while(rsMovieGenres.next()) {
                    MovieGenre movieGenre = new MovieGenre() {
                        {
                            setGenre(new Genre() { {
                                setGenreID(rsMovieGenres.getInt("ggenreID"));
                                setName(rsMovieGenres.getString("gname"));
                            }});
                            setMovie(movie);
                        }  
                    };
                    movie.getMovieGenres().add(movieGenre);
                }
                //LOAD PRODUCTIONS
                String sqlProductions = "SELECT pc.name as pcname, pc.pcID FROM movie m "
                        + "JOIN production p ON (m.movieID = p.movieID) "
                        + "JOIN productioncompany pc ON (pc.pcID = p.productioncompanyID)"
                        + "WHERE p.movieID = " + movie.getMovieID();
                Statement statementProductions = connection.createStatement();
                ResultSet rsProductions = statementProductions.executeQuery(sqlProductions);
                
                while(rsProductions.next()) {
                    Production production = new Production() {
                        {
                            setProductionCompany(new ProductionCompany() { {
                                setProductionCompanyID(rsProductions.getInt("pcID"));
                                setName(rsProductions.getString("pcname"));
                            }});
                            setMovie(movie);
                        }  
                    };
                    movie.getProductions().add(production);
                }
                
                movies.add(movie);
            }
            
            statement.close();
            rs.close();
            
            return movies;
        } catch (SQLException ex) {
            Logger.getLogger(DbUserRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error loading movies!");
        }
    }

    @Override
    public void insert(Movie movie) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            
            //INSERT MOVIE
            String sql = "INSERT INTO movie (movieID, name, releasedate, score, description, directorid) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setInt(1, movie.getMovieID());
            statement.setString(2, movie.getName());
            statement.setObject(3, movie.getReleaseDate(), java.sql.Types.DATE);
            statement.setDouble(4, movie.getScore());
            statement.setString(5, movie.getDescription());
            statement.setInt(6, movie.getDirector().getDirectorID());
            
            statement.executeUpdate();
            ResultSet rsKey = statement.getGeneratedKeys();
            
            if (rsKey.next()) {
                int id = rsKey.getInt(1);
                movie.setMovieID(id);
                
                //INSERT ROLES
                sql = "INSERT INTO role (actorID, movieID, rolename) VALUES (?, ?, ?)";
                statement = connection.prepareStatement(sql);
                for (Role role : movie.getRoles()) {
                    statement.setInt(1, role.getActor().getActorID());
                    statement.setInt(2, movie.getMovieID());
                    statement.setString(3, role.getRoleName());
                    statement.executeUpdate();
                }
                
                //INSERT MOVIE GENRES
                sql = "INSERT INTO movie_genre (genreID, movieID) VALUES (?, ?)";
                statement = connection.prepareStatement(sql);
                for (MovieGenre movieGenre : movie.getMovieGenres()) {
                    statement.setInt(1, movieGenre.getGenre().getGenreID());
                    statement.setInt(2, movie.getMovieID());
                    statement.executeUpdate();
                }
                
                //INSERT PRODUCTIONS
                sql = "INSERT INTO production (productioncompanyID, movieID) VALUES (?, ?)";
                statement = connection.prepareStatement(sql);
                for (Production production : movie.getProductions()) {
                    statement.setInt(1, production.getProductionCompany().getProductionCompanyID());
                    statement.setInt(2, movie.getMovieID());
                    statement.executeUpdate();
                }
                
                statement.close();
                rsKey.close();
            } else {
                throw new Exception("Error inserting movie");
            }
            
            statement.close();
        } catch(SQLException e) {
            throw new Exception(e.getMessage());
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
            
            //UPDATE BASIC MOVIE INFO
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
            
            //REMOVE ROLES RELATED TO MOVIE
            sql = "DELETE FROM role WHERE movieID=" + movie.getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            
            //INSERT ROLES
            sql = "INSERT INTO role (actorID, movieID, rolename) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            for (Role role : movie.getRoles()) {
                statement.setInt(1, role.getActor().getActorID());
                statement.setInt(2, movie.getMovieID());
                statement.setString(3, role.getRoleName());
                statement.executeUpdate();
            }
            
            //REMOVE MOVIE GENRES RELATED TO MOVIE
            sql = "DELETE FROM movie_genre WHERE movieID=" + movie.getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            //INSERT MOVIE GENRES
            sql = "INSERT INTO movie_genre (genreID, movieID) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            for (MovieGenre movieGenre : movie.getMovieGenres()) {
                statement.setInt(1, movieGenre.getGenre().getGenreID());
                statement.setInt(2, movie.getMovieID());
                statement.executeUpdate();
            }
            
            //REMOVE PRODUCTIONS RELATED TO MOVIE
            sql = "DELETE FROM production WHERE movieID=" + movie.getMovieID();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            //INSERT PRODUCTIONS
            sql = "INSERT INTO production (productioncompanyID, movieID) VALUES (?, ?)";
            statement = connection.prepareStatement(sql);
            for (Production production : movie.getProductions()) {
                statement.setInt(1, production.getProductionCompany().getProductionCompanyID());
                statement.setInt(2, movie.getMovieID());
                statement.executeUpdate();
            }
        
        } catch(Exception ex) {
            throw new Exception("Error updating movie!");
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
