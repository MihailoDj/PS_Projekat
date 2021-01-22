/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import comm.Response;
import communication.Communication;
import domain.User;
import util.Constants;
import view.coordinator.MainCoordinator;

/**
 *
 * @author Mihailo
 */
public class ServerResponseHandler extends Thread{

    @Override
    public void run() {
        try {
            while (true) {            
                Response response = Communication.getInstance().receiveServerResponse();
                switch(response.getOperation()){
                    case LOGIN:
                        User user = (User)response.getResult();
                        MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, user);
                        break;
                    /*case LOGOUT:
                        ClientController.getInstance().logout();
                        break;
                    case LOGOUT_ALL:
                        ClientController.getInstance().turnServerOff();
                        break;
                    */

                }

            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    
}
