/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
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
        prepareView();
        
        frmViewReviews.setVisible(true);
    }

    private void prepareView() {
        try {
            ReviewTableModel rtm = new ReviewTableModel();
            frmViewReviews.getTblReviews().setModel(rtm);
            setUpTableColumns();
        } catch (Exception ex) {
            Logger.getLogger(ViewAllReviewsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(frmViewReviews, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setUpTableColumns() throws Exception {

        TableColumnModel tcm = frmViewReviews.getTblReviews().getColumnModel();

        frmViewReviews.getTblReviews().setAutoCreateRowSorter(true);
        frmViewReviews.getTblReviews().getTableHeader().setResizingAllowed(false);

        frmViewReviews.getTblReviews().setRowHeight(30);
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
        tcm.getColumn(2).setPreferredWidth(15);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(100);
    }

    private void addActionListener() {
        frmViewReviews.btnRemoveReviewActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        frmViewReviews.btnUpdateReviewActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
}
