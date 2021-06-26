/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;
import comm.Operation;
import comm.Request;
import communication.Communication;
import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Constants;
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
                            
                            Request request = new Request(Operation.DELETE_USER, user);
                            Communication.getInstance().sendUserRequest(request);
                            
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
                            validateForm();
                            
                            ((User)MainCoordinator.getInstance().getParam(
                                    Constants.CURRENT_USER)).setUsername(form.getTxtUsername().getText().trim());
                            ((User)MainCoordinator.getInstance().getParam(
                                    Constants.CURRENT_USER)).setPassword(String.copyValueOf(form.getTxtNewPassword().getPassword()));

                            Request request = new Request(Operation.UPDATE_USER, (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                            Communication.getInstance().sendUserRequest(request);
                            
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
        
        form.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.getBtnDeactivate().setEnabled(true);
                form.getBtnUpdate().setEnabled(true);
                
                form.getTxtNewPassword().setEditable(true);
                form.getTxtOldPassword().setEditable(true);
                form.getTxtUsername().setEditable(true);
            }
        });
    }
    
    private void prepareView() {
       try {
            User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
            
            form.getTxtUsername().setText(user.getUsername());
        } catch (Exception ex) {
            Logger.getLogger(AccountSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void validateForm() throws Exception{
        String oldPass = String.copyValueOf(form.getTxtOldPassword().getPassword());
        String newPass = String.copyValueOf(form.getTxtNewPassword().getPassword());
        String username = form.getTxtUsername().getText();
        if (username.isEmpty() || oldPass.isEmpty() || newPass.isEmpty())
            throw new Exception("Fields can't be empty!");
        if (newPass.equals(oldPass))
            throw new Exception("Your new password can't be the same as your old password!");
    }
}
