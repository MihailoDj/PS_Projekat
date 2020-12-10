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
import view.form.util.FormMode;

/**
 *
 * @author Mihailo
 */
public class ReviewController {
    private final FrmReview frmReview;
    private Review review;
    
    public ReviewController(FrmReview frmReview) {
        this.frmReview = frmReview;
        addActionListener();
    }
    
    public void openForm(FormMode formMode) {
        prepareView(formMode);
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
                try{
                    validateForm();
                    
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
                
                    Controller.getInstance().insertReview(review);
                    JOptionPane.showMessageDialog(frmReview, movie.getName() + " successfully reviewd!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmReview.dispose();
                } catch(Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmReview, "Unable to review movie!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frmReview.btnUpdateActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(frmReview, "Are you sure?", "Update review", JOptionPane.YES_NO_OPTION);
                
                try{
                    validateForm();
                    
                    if (check == JOptionPane.YES_OPTION) {
                        Movie movie = (Movie)MainCoordinator.getInstance().getParam(Constants.PARAM_MOVIE);
                        User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                        Review review = new Review() {
                            {
                                setReviewText(frmReview.getTxtReviewText().getText().trim());
                                setReviewScore(Integer.parseInt(frmReview.getTxtReviewScore().getText().trim()));
                                setUser(user);
                                setMovie(movie);
                            }
                        };
                        Controller.getInstance().updateReview(review);
                        JOptionPane.showMessageDialog(frmReview, "Review successfully updated", 
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        frmReview.dispose();
                    }
                } catch(Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmReview, "Unable to update review", 
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        });
    }
    
    private void prepareView(FormMode formMode) {
        Movie movie = ((Review)MainCoordinator.getInstance().getParam(Constants.PARAM_REVIEW)).getMovie();
        frmReview.getLblMovieName().setText(movie.getName());
        
        setUpComponents(formMode);
    }

    private void setUpComponents(FormMode formMode) {
        switch(formMode) {
            case FORM_ADD:
                review = new Review();
                frmReview.getTxtReviewScore().setText("");
                frmReview.getTxtReviewText().setText("");
                
                frmReview.getBtnSave().setEnabled(true);
                frmReview.getBtnCancel().setEnabled(true);
                frmReview.getBtnUpdate().setEnabled(false);
                break;
            case FORM_EDIT:
                review = ((Review)MainCoordinator.getInstance().getParam(Constants.PARAM_REVIEW));
                
                frmReview.getTxtReviewScore().setText(review.getReviewScore() + "");
                frmReview.getTxtReviewText().setText(review.getReviewText());
                
                frmReview.getBtnSave().setEnabled(false);
                frmReview.getBtnCancel().setEnabled(true);
                frmReview.getBtnUpdate().setEnabled(true);
                break;
        }
    }
    
    private void validateForm() throws Exception {
        if (frmReview.getTxtReviewScore().getText().isEmpty() || frmReview.getTxtReviewText().getText().isEmpty()) {
            throw new Exception("Fields can't be empty!");
        }
    }
}
