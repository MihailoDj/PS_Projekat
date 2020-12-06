/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.image.BufferedImage;

/**
 *
 * @author Mihailo
 */
public class MoviePoster {
    private int moviePosterID;
    private BufferedImage posterImage;

    public MoviePoster() {
    }

    public MoviePoster(int moviePosterID, BufferedImage posterImage) {
        this.moviePosterID = moviePosterID;
        this.posterImage = posterImage;
    }

    public int getMoviePosterID() {
        return moviePosterID;
    }

    public void setMoviePosterID(int moviePosterID) {
        this.moviePosterID = moviePosterID;
    }

    public BufferedImage getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(BufferedImage posterImage) {
        this.posterImage = posterImage;
    }
    
    
}
