/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import controller.Controller;
import domain.Movie;
import domain.Review;
import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmReview;

/**
 *
 * @author Mihailo
 */
public class ReviewController {
    private final FrmReview frmReview;
    
    public ReviewController(FrmReview frmReview) {
        this.frmReview = frmReview;
        prepareView();
        addActionListener();
    }
    
    public void openForm() {
        frmReview.setTitle("Movie review");
        frmReview.setLocationRelativeTo(null);
        frmReview.setVisible(true);
    }
    
    private void addActionListener() {
        frmReview.btnCancelActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmReview.dispose();
            }
        });
        frmReview.btnSaveActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Movie movie = (Movie)MainCoordinator.getInstance().getParam(Constants.PARAM_MOVIE);
                User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                Review review = new Review() {
                    {
                        setReviewID(0);
                        setReviewText(frmReview.getTxtReviewText().getText().trim());
                        setReviewScore(Integer.parseInt(frmReview.getTxtReviewScore().getText().trim()));
                        setReviewDate(LocalDateTime.now());
                        setUser(user);
                        setMovie(movie);
                    }
                };
                
                System.out.println(review.toString());
                try{
                    Controller.getInstance().insertReview(review);
                    JOptionPane.showMessageDialog(frmReview, movie.getName() + " successfully reviewd!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmReview.dispose();
                } catch(Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmReview, "Unable to review movie", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    private void prepareView() {
        Movie movie = (Movie)MainCoordinator.getInstance().getParam(Constants.PARAM_MOVIE);
        frmReview.getLblMovieName().setText(movie.getName());
    }
}
