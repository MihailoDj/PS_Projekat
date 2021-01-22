/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import view.coordinator.MainCoordinator;

/**
 *
 * @author Mihailo
 */
public class Main {
    public static void main(String[] args) {
        MainCoordinator.getInstance().openLoginForm();
    }
}
