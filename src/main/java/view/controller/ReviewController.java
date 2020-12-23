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
    private boolean isFormModeAdd = true;
    
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
                    if(isFormModeAdd)
                        frmReview.getBtnSave().setEnabled(false);
                    else
                        frmReview.getBtnUpdate().setEnabled(false);
                else
                    if(isFormModeAdd)
                        frmReview.getBtnSave().setEnabled(true);
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
                    review.setReviewScore(frmReview.getStarRater().getSelection());
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
                        review.setReviewScore(frmReview.getStarRater().getSelection());
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
                isFormModeAdd = true;
                
                review = new Review();
                frmReview.getStarRater().setSelection(0);
                frmReview.getTxtReviewText().setText("");
                frmReview.getLblCharactersRemaining().setText("Characters remaining: 300");
                
                frmReview.getBtnSave().setEnabled(true);
                frmReview.getBtnCancel().setEnabled(true);
                frmReview.getBtnUpdate().setEnabled(false);
                break;
            case FORM_EDIT:
                isFormModeAdd = false;
                
                review = ((Review)MainCoordinator.getInstance().getParam(Constants.PARAM_REVIEW));
                
                frmReview.getStarRater().setRating(review.getReviewScore());
                frmReview.getTxtReviewText().setText(review.getReviewText());
                frmReview.getLblCharactersRemaining().setText("Characters remaining: " + (300 - review.getReviewText().length()));
                
                frmReview.getBtnSave().setEnabled(false);
                frmReview.getBtnCancel().setEnabled(true);
                frmReview.getBtnUpdate().setEnabled(true);
                break;
            case FORM_VIEW:
                isFormModeAdd = false;
                
                review = ((Review)MainCoordinator.getInstance().getParam(Constants.PARAM_REVIEW));
                
                frmReview.getStarRater().setRating(review.getReviewScore());
                frmReview.getTxtReviewText().setText(review.getReviewText());
                
                frmReview.remove(frmReview.getBtnSave());
                frmReview.remove(frmReview.getBtnCancel());
                frmReview.remove(frmReview.getBtnUpdate());
                
                frmReview.getStarRater().setEnabled(false);
                frmReview.getTxtReviewText().setEditable(false);
                
                break;
        }
    }
    
    private void validateForm() throws Exception {
        String reviewText = frmReview.getTxtReviewText().getText();
        
        if (frmReview.getStarRater().getSelection() == 0)
            throw new Exception("Review score can't be 0!");
        if (reviewText.isEmpty()) 
            throw new Exception("Fields can't be empty!");
        if (reviewText.length() > 300)
            throw new Exception("Limit review to 300 characters!");
    }
}
