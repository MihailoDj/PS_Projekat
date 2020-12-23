/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.User;
import domain.UserStatistics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.TableColumnModel;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmMain;
import view.form.component.table.UserStatisticsTableModel;

/**
 *
 * @author Mihailo
 */
public class MainController {
    private final FrmMain frmMain;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListener();
    }

    public void openForm() {
        frmMain.setTitle("Main form");
        frmMain.setLocationRelativeTo(null);
        
        User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        String systemRole = (user.isAdmin()) ? "administrator" : "user";
        frmMain.getLblCurrentUser().setText("Current " + systemRole + ": " + user.getUsername());
        
        setUpTableuserStatistics();
        
        frmMain.setVisible(true);
        frmMain.setExtendedState( JFrame.MAXIMIZED_BOTH);
    }

    private void addActionListener() {
        frmMain.jmiLogoutActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmMain.dispose();
                MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, null);
                MainCoordinator.getInstance().openLoginForm();
            }
        });
        frmMain.jmiNewMovieAddActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductNewActionPerformed(evt);
            }

            private void jmiProductNewActionPerformed(java.awt.event.ActionEvent evt) {
                MainCoordinator.getInstance().openMovieForm();
            }
        });
        frmMain.jmiViewAllMoviesActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProductShowAllActionPerformed(evt);
            }

            private void jmiProductShowAllActionPerformed(java.awt.event.ActionEvent evt) {
                MainCoordinator.getInstance().openViewAllMoviesForm();
            }
        });
        frmMain.jmiNewDirectorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmiNewDirectorActionPerformed(evt);
            }
            
            private void jmiNewDirectorActionPerformed(ActionEvent evt) {
                MainCoordinator.getInstance().openDirectorForm();
            }
        });
        frmMain.jmiViewAllDirectorsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmiViewAllDirectorsActionPerformed(evt);
            }
            
            private void jmiViewAllDirectorsActionPerformed(ActionEvent evt) {
                MainCoordinator.getInstance().openViewAllDirectorsForm();
            }
        });
        frmMain.jmiNewActorAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmiNewActorActionPerformed(evt);
            }
            
            private void jmiNewActorActionPerformed(ActionEvent evt) {
                MainCoordinator.getInstance().openActorForm();
            }
        });
        frmMain.jmiViewAllActorsActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmiViewAllActorsActionPerformed(evt);
            }
            
            private void jmiViewAllActorsActionPerformed(ActionEvent evt) {
                MainCoordinator.getInstance().openViewAllActorsForm();
            }
        });
    }
    
    public FrmMain getFrmMain() {
        return frmMain;
    }
    
    public void setUpTableuserStatistics() {
        try {
            List<UserStatistics> userStats = Communication.getInstance().selectUserStatistics();
            
            UserStatisticsTableModel ustm = new UserStatisticsTableModel(userStats);
            frmMain.getTblUserStatistics().setModel(ustm);
            
            TableColumnModel tcm = frmMain.getTblUserStatistics().getColumnModel();

            frmMain.getTblUserStatistics().setAutoCreateRowSorter(true);
            frmMain.getTblUserStatistics().getTableHeader().setResizingAllowed(false);

            frmMain.getTblUserStatistics().setRowHeight(30);
            tcm.getColumn(0).setPreferredWidth(15);
            tcm.getColumn(1).setPreferredWidth(150);
            tcm.getColumn(2).setPreferredWidth(100);
            tcm.getColumn(3).setPreferredWidth(100);
            tcm.getColumn(4).setPreferredWidth(100);
            tcm.getColumn(5).setPreferredWidth(150);
            tcm.getColumn(6).setPreferredWidth(35);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
