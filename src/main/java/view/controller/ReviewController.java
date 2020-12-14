/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import communication.Communication;
import domain.Movie;
import domain.Review;
import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        addKeyListener();
    }
    
    public void openForm(FormMode formMode) {
        prepareView(formMode);
        frmReview.setTitle("Movie review");
        frmReview.setLocationRelativeTo(null);
        frmReview.setVisible(true);
    }
    
    private void addKeyListener() {
        frmReview.txtReviewTextKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int reviewLength = frmReview.getTxtReviewText().getText().length();
                int charsRemaining = 300 - reviewLength;
                frmReview.getLblCharactersRemaining().setText("Characters remaining: " + charsRemaining);
                
                if (charsRemaining < 0)
                    frmReview.getBtnUpdate().setEnabled(false);
                else
                    frmReview.getBtnUpdate().setEnabled(true);
            }
        });
    };
    
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
                    
                    Review review = new Review();
                    review.setReviewID(0l);
                    review.setReviewText(frmReview.getTxtReviewText().getText().trim());
                    review.setReviewScore(Integer.parseInt(frmReview.getTxtReviewScore().getText().trim()));
                    review.setReviewDate(LocalDateTime.now());
                    review.setUser(user);
                    review.setMovie(movie);
                
                    Communication.getInstance().insertReview(review);
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
                        
                        Review review = new Review();
                        review.setReviewText(frmReview.getTxtReviewText().getText().trim());
                        review.setReviewScore(Integer.parseInt(frmReview.getTxtReviewScore().getText().trim()));
                        review.setUser(user);
                        review.setMovie(movie);
                        
                        Communication.getInstance().updateReview(review);
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
                frmReview.getLblCharactersRemaining().setText("Characters remaining: 300");
                
                frmReview.getBtnSave().setEnabled(true);
                frmReview.getBtnCancel().setEnabled(true);
                frmReview.getBtnUpdate().setEnabled(false);
                break;
            case FORM_EDIT:
                review = ((Review)MainCoordinator.getInstance().getParam(Constants.PARAM_REVIEW));
                
                frmReview.getTxtReviewScore().setText(review.getReviewScore() + "");
                frmReview.getTxtReviewText().setText(review.getReviewText());
                frmReview.getLblCharactersRemaining().setText("Characters remaining: " + (300 - review.getReviewText().length()));
                
                frmReview.getBtnSave().setEnabled(false);
                frmReview.getBtnCancel().setEnabled(true);
                frmReview.getBtnUpdate().setEnabled(true);
                break;
        }
    }
    
    private void validateForm() throws Exception {
        String reviewScore = frmReview.getTxtReviewScore().getText();
        String reviewText = frmReview.getTxtReviewText().getText();
        
        if (reviewText.isEmpty() || reviewScore.isEmpty()) 
            throw new Exception("Fields can't be empty!");
        if (Integer.parseInt(reviewScore) < 1 || Integer.parseInt(reviewScore) > 10)
            throw new Exception("Review score must be an integer between 1 and 10");
        if (reviewScore.length() > 300)
            throw new Exception("Limit review to 300 characters!");
    }
}
