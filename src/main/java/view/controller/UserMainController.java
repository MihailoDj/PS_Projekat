/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.constant.Constants;
import view.coordinator.MainCoordinator;
import view.form.FrmUserMain;

/**
 *
 * @author Mihailo
 */
public class UserMainController {
    private final FrmUserMain frmUserMain;

    public UserMainController(FrmUserMain frmUserMain) {
        this.frmUserMain = frmUserMain;
        addActionListener();
    }

    public void openForm() {
        frmUserMain.setTitle("Homepage");
        frmUserMain.setLocationRelativeTo(null);
        
        User user = (User)MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        String systemRole = (user.isAdmin()) ? "administrator" : "user";
        frmUserMain.getLblCurrentUser().setText("Current " + systemRole + ": " + user.getUsername());
        
        frmUserMain.setVisible(true);
    }
    
    private void addActionListener() {
        frmUserMain.jmiAccountSettings(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAccountSettingsForm();
            }
        });
    }
    
    public FrmUserMain getFrmUserMain() {
        return frmUserMain;
    }
}
