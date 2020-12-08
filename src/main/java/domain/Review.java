/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.time.LocalDateTime;

/**
 *
 * @author Mihailo
 */
public class Review {
    private int reviewID;
    private String reviewText;
    private int reviewScore;
    private LocalDateTime reviewDate;
    private Movie movie;
    private User user;

    public Review() {
    }

    public Review(int reviewID, String reviewText, int reviewScore, Movie movie, User user) {
        this.reviewID = reviewID;
        this.reviewText = reviewText;
        this.reviewScore = reviewScore;
        this.movie = movie;
        this.user = user;
        this.reviewDate = LocalDateTime.now();
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" + "reviewID=" + reviewID + ", reviewText=" + reviewText + ", reviewScore=" + reviewScore + ", reviewDate=" + reviewDate + ", movie=" + movie + ", user=" + user + '}';
    }
    
    
}
