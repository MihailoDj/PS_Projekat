/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import controller.Controller;
import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmAccountSettings;

/**
 *
 * @author Mihailo
 */
public class AccountSettingsController {
    private final FrmAccountSettings form;

    public AccountSettingsController(FrmAccountSettings form) {
        this.form = form;
        addActionListener();
    }
    
    public void openForm() {
        form.setTitle("Settings");
        form.setLocationRelativeTo(null);
        prepareView();
        form.setVisible(true);
        
    }
    
    private void addActionListener() {
        form.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.dispose();
            }
        });
        form.btnDeactivateAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(form, "Are you sure you want to deactivate your account?", 
                        "Account deactivation", JOptionPane.YES_NO_OPTION);
                
                if(check == JOptionPane.YES_OPTION) {
                    String oldPass = ((User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER)).getPassword();
                    if (String.copyValueOf(form.getTxtOldPassword().getPassword()).equals(oldPass)) {
                        try {
                            User user = ((User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                            Controller.getInstance().deleteUser(user);
                            JOptionPane.showMessageDialog(form, "Account successfully deleted", 
                                    "Goodbye!" ,JOptionPane.INFORMATION_MESSAGE);
                            
                            form.dispose();
                            MainCoordinator.getInstance().getUserMainController().getFrmUserMain().dispose();
                            MainCoordinator.getInstance().openLoginForm();
                        } catch (Exception ex) {
                            Logger.getLogger(AccountSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(form, "You must enter your old password correctly",
                                "Error" , JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        form.btnUpdateAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check = JOptionPane.showConfirmDialog(form, "Are you sure you want to change your account info?", 
                        "Update account", JOptionPane.YES_NO_OPTION);
                
                if(check == JOptionPane.YES_OPTION) {
                    String oldPass = ((User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER)).getPassword();
                    if (String.copyValueOf(form.getTxtOldPassword().getPassword()).equals(oldPass)) {
                        try {
                            ((User)MainCoordinator.getInstance().getParam(
                                    Constants.CURRENT_USER)).setUsername(form.getTxtUsername().getText().trim());
                            ((User)MainCoordinator.getInstance().getParam(
                                    Constants.CURRENT_USER)).setPassword(String.copyValueOf(form.getTxtNewPassword().getPassword()));

                            Controller.getInstance().updateUser((User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                            JOptionPane.showMessageDialog(form, "Account info successfully updated", 
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            Logger.getLogger(AccountSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(form, "Error updating account info", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(form, "You must enter your old password correctly", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    
    private void prepareView() {
        try {
            String username = ((User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER)).getUsername();
            User user = Controller.getInstance().selectUser(username).get(0);
            
            form.getTxtUsername().setText(user.getUsername());
            MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, user);
        } catch (Exception ex) {
            Logger.getLogger(AccountSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}