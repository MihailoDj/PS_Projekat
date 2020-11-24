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
import javax.swing.JOptionPane;
import view.coordinator.MainCoordinator;
import view.form.FrmLogin;

/**
 *
 * @author Mihailo
 */
public class LoginController {
    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }

    public void openForm() {
        frmLogin.setVisible(true);
    }

    private void addActionListener() {
        frmLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                login(actionEvent);
            }

            private void login(ActionEvent actionEvent) {
                resetErrorFields();
                try {
                    String username = frmLogin.getTxtUsername().getText().trim();
                    String password = String.copyValueOf(frmLogin.getTxtPassword().getPassword());

                    validateForm(username, password);

                    User user = Controller.getInstance().login(username, password);
                    JOptionPane.showMessageDialog(
                            frmLogin, "Welcome " + user.toString(), "Login successful", JOptionPane.INFORMATION_MESSAGE);
                    
                    frmLogin.dispose();

                    MainCoordinator.getInstance().openMainForm();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmLogin, e.getMessage(), "Login error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void resetErrorFields() {
                frmLogin.getLblUsernameError().setText("");
                frmLogin.getLblPasswordError().setText("");
            }

            private void validateForm(String username, String password) throws Exception {
                String errorMessage = "";
                
                if (username.isEmpty()) {
                    frmLogin.getLblUsernameError().setText("Username can not be empty!");
                    errorMessage += "Username can not be empty!\n";
                }
                if (password.isEmpty()) {
                    frmLogin.getLblPasswordError().setText("Password can not be empty!");
                    errorMessage += "Password can not be empty!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }
        });
    }
}
