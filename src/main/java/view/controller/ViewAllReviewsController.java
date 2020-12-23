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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmViewReviews;
import view.form.component.table.ReviewTableModel;

/**
 *
 * @author Mihailo
 */
public class ViewAllReviewsController {
    private final FrmViewReviews frmViewReviews;
    
    public ViewAllReviewsController(FrmViewReviews frmViewReviews) {
        this.frmViewReviews = frmViewReviews;
        addActionListener();
        addListSelectionListener();
    }
    
    
    public void openForm() {
        frmViewReviews.setLocationRelativeTo(null);
        frmViewReviews.setTitle("My reviews");
        frmViewReviews.setVisible(true);
    }
    
    public void setUpTableColumns() throws Exception {

        TableColumnModel tcm = frmViewReviews.getTblReviews().getColumnModel();

        frmViewReviews.getTblReviews().setAutoCreateRowSorter(true);
        frmViewReviews.getTblReviews().getTableHeader().setResizingAllowed(false);

        frmViewReviews.getTblReviews().setRowHeight(30);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(150);
        tcm.getColumn(2).setPreferredWidth(150);
        tcm.getColumn(3).setPreferredWidth(35);
        tcm.getColumn(4).setPreferredWidth(150);
        tcm.getColumn(5).setPreferredWidth(150);
    }

    private void addActionListener() {
        frmViewReviews.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblReviews();
            }
            
        });
        frmViewReviews.btnRemoveReviewActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(frmViewReviews, "Delete review", "Are you sure?", JOptionPane.YES_NO_OPTION);
                int row = frmViewReviews.getTblReviews().getSelectedRow();
                
                if (check == JOptionPane.YES_OPTION) {
                    try {
                        Review review = ((ReviewTableModel)frmViewReviews.getTblReviews().getModel()).getReviewAt(row);
                        Communication.getInstance().deleteReview(review);
                        JOptionPane.showMessageDialog(frmViewReviews, "Review deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        Logger.getLogger(ViewAllReviewsController.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(frmViewReviews, "Unable to delete review", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        frmViewReviews.btnUpdateReviewActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = frmViewReviews.getTblReviews().getSelectedRow();
                Review review = ((ReviewTableModel)frmViewReviews.getTblReviews().getModel()).getReviewAt(row);
                Movie movie = review.getMovie();
                
                MainCoordinator.getInstance().addParam(Constants.PARAM_MOVIE, movie);
                MainCoordinator.getInstance().addParam(Constants.PARAM_REVIEW, review);
                MainCoordinator.getInstance().openReviewEditForm();
            }
        });
    }

    private void addListSelectionListener() {
        frmViewReviews.tblReviewsListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmViewReviews.getTblReviews().getSelectedRow() != -1) {
                    frmViewReviews.getBtnRemove().setEnabled(true);
                    frmViewReviews.getBtnUpdate().setEnabled(true);
                } else{
                    frmViewReviews.getBtnRemove().setEnabled(false);
                    frmViewReviews.getBtnUpdate().setEnabled(false);
                }
                
            }
        });
    }
    
    private void fillTblReviews() {
        try {
            User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
            Review review = new Review();
            review.setUser(user);
            
            List<Review> reviews = Communication.getInstance().selectReviews(review);
            ReviewTableModel rtm = new ReviewTableModel(reviews);
            frmViewReviews.getTblReviews().setModel(rtm);
            setUpTableColumns();
        } catch (Exception ex) {
            Logger.getLogger(ViewAllReviewsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmViewReviews, "Error loading table", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
