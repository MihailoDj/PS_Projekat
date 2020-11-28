/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmMain;

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
        
        frmMain.setVisible(true);
        frmMain.setExtendedState( JFrame.MAXIMIZED_BOTH);
    }

    private void addActionListener() {
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
    }
    
    public FrmMain getFrmMain() {
        return frmMain;
    }
}
