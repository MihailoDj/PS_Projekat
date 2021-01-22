/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import comm.Response;
import comm.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.ServerResponseHandler;

/**
 *
 * @author Mihailo
 */
public class Communication {
    private static Communication instance;
    private Socket socket;
    
    private Communication() throws Exception{
        socket = new Socket("localhost", 4000);
        ServerResponseHandler srh = new ServerResponseHandler();
        srh.start();
    }
    
    public static Communication getInstance() throws Exception{
        if (instance == null) 
            instance = new Communication();
        return instance;
    }
    
    public static void stopCommunication() {
        instance = null;
    }
    
    public void sendUserRequest(Request request){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Response receiveServerResponse(){
        Response response = new Response();
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            response = (Response) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
}
