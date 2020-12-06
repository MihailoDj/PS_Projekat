/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author Mihailo
 */
public class Movie {
    private int movieID;
    private String name;
    private LocalDate releaseDate;
    private String description;
    private double score;
    private Director director;
    private List<Role> roles;
    private List<MovieGenre> movieGenres;
    private List<Production> productions;
    private MoviePoster moviePoster;
    
    
    public Movie() {
        this.roles = new ArrayList<Role>();
        this.movieGenres = new ArrayList<MovieGenre>();
        this.productions = new ArrayList<Production>();
        this.moviePoster = new MoviePoster();
    }

    public Movie(int movieID, String name, LocalDate releaseDate, String description, double score, Director director) {
        this.movieID = movieID;
        this.name = name;
        this.releaseDate = releaseDate;
        this.description = description;
        this.score = score;
        this.director = director;
        this.roles = new ArrayList<Role>();
        this.movieGenres = new ArrayList<MovieGenre>();
        this.productions = new ArrayList<Production>();
        this.moviePoster = new MoviePoster();
    }
    
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<MovieGenre> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(List<MovieGenre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public void setProductions(List<Production> productions) {
        this.productions = productions;
    }
    
    public MoviePoster getMoviePoster() {
        return moviePoster;
    }
    
    public void setMoviePoster(MoviePoster moviePoster) {
        this.moviePoster = moviePoster;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.movieID;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.releaseDate);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.score) ^ (Double.doubleToLongBits(this.score) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.director);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.director, other.director)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
