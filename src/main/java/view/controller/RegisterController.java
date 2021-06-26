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
import view.form.FrmRegister;

/**
 *
 * @author Mihailo
 */
public class RegisterController {
    private final FrmRegister frmRegister;

    public RegisterController(FrmRegister frmRegister) {
        this.frmRegister = frmRegister;
        addActionListener();
    }

    public void openForm() {
        frmRegister.setVisible(true);
        frmRegister.setLocationRelativeTo(null);
        frmRegister.setTitle("Register");
    }
    
    public void addActionListener() {
        frmRegister.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmRegister.dispose();
            }
        });
        frmRegister.btnConfirmAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    validateForm();
                    
                    User user = new User();
                    user.setUserID(0l);
                    user.setUsername(frmRegister.getTxtUsername().getText().trim());
                    user.setPassword(String.copyValueOf(frmRegister.getTxtPassword().getPassword()));
                    user.setEmail(frmRegister.getTxtEmail().getText().trim());
                    user.setStatus("offline");
                   
                    Request request = new Request(Operation.INSERT_USER, user);
                    Communication.getInstance().sendUserRequest(request);
                    
                } catch (Exception ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmRegister, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    private void validateForm() throws Exception{
        String username = frmRegister.getTxtUsername().getText().trim();
        String password = String.copyValueOf(frmRegister.getTxtPassword().getPassword());
        String email = frmRegister.getTxtEmail().getText().trim();
        String passwordRepeat = String.copyValueOf(frmRegister.getTxtRepeatPassword().getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            throw new Exception("Username and password can't be empty!");
        }
        
        if (!email.contains("@") && !email.contains(".com")) {
            throw new Exception("Invalid email address!");
        }
        
        if (!password.equals(passwordRepeat)) {
            throw new Exception("Please correctly retype your password!");
        }
    }

    public FrmRegister getFrmRegister() {
        return frmRegister;
    }
    
    
}
