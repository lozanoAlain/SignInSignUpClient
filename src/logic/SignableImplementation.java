/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import dataModel.DataEncapsulation;
import dataModel.MessageEnum;
import dataModel.Signable;
import dataModel.User;
import exceptions.ConnectionErrorException;
import exceptions.ExistUserException;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotExistException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;

/**
 * This class is for getting the connection between the server and the client,
 * sending the dataEncapsulation that is collected in the Sign In window or Sign
 * Up window, and takes the dataEncapsulation we receive from the server and
 * sends it to the required window
 *
 * @author Alain Lozano, Ilia Consuegra
 */
public class SignableImplementation implements Signable {
    //The parameters for the socket are taken from the configuration file
    private final static int PORT = Integer.valueOf(ResourceBundle.getBundle("dataModel.ClientConfiguration").getString("Port"));
    private final static String IP = ResourceBundle.getBundle("dataModel.ClientConfiguration").getString("ServerHost");

    private DataEncapsulation data = null;

    /**
     * The method that receives the user and sends it to the server to check the
     * database and log in.
     *
     * @param user The user that is collected from the window
     * @return user that is returned to the window from the database
     * @throws UserNotExistException Is thrown in case the user do not exist in
     * the database
     * @throws IncorrectPasswordException Is thrown in case the password is
     * incorrect for the user that is sent
     * @throws ConnectionErrorException Is thrown in case there is an error in
     * the connection between the server and the client
     */
    @Override
    public User signIn(User user) throws UserNotExistException, IncorrectPasswordException, ConnectionErrorException {
        Socket sc = null;
        try {
            //The dataEncapsulation object is sent to the server application
            sc = new Socket(IP, PORT);
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(sc.getOutputStream());
            data = new DataEncapsulation();
            data.setUser(user);
            data.setMessage(MessageEnum.SIGN_IN);
            oos.writeObject(data);

            //The dataEncapsulation object is received from the server 
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(sc.getInputStream());
            data = (DataEncapsulation) ois.readObject();
            user = data.getUser();
            int message = data.getMessage().ordinal();
            oos.close();
            ois.close();
            sc.close();
            //The message are analyzed to throw the exceptions
            switch (data.getMessage()) {
                case SIGN_IN_OK:
                    return user;
                case SIGN_IN_ERROR_USER:
                    throw new UserNotExistException();
                case SIGN_IN_ERROR_PASSWORD:
                    throw new IncorrectPasswordException();
                case CONNECTION_ERROR:
                    throw new ConnectionErrorException();
                default:
                    throw new ConnectionErrorException();
            }
        } catch (java.net.ConnectException ex) {
            throw new ConnectionErrorException();
        } catch (ClassNotFoundException ex) {
            throw new ConnectionErrorException();
        } catch (IOException ex) {
            throw new ConnectionErrorException();
        } 

    }

    /**
     * The method that receives the user and sends it to the server to check the
     * database and log up.
     *
     * @param user The user that is collected from the window
     * @throws ExistUserException Is thrown in case the user exists in the
     * database
     * @throws ConnectionErrorException Is thrown in case there is an error in
     * the connection between the server and the client
     */
    @Override
    public void signUp(User user) throws ExistUserException, ConnectionErrorException {
        Socket sc;
        try {
            //The dataEncapsulation object is sent to the server application
            sc = new Socket(IP, PORT);
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(sc.getOutputStream());
            data = new DataEncapsulation();
            data.setUser(user);
            data.setMessage(MessageEnum.SIGN_UP);
            oos.writeObject(data);

            //The dataEncapsulation object is received from the server 
            ObjectInputStream ois = null;
            ois = new ObjectInputStream(sc.getInputStream());
            data = (DataEncapsulation) ois.readObject();
            user = data.getUser();
            oos.close();
            ois.close();
            sc.close();
            //The message are analyzed to throw the exceptions
            switch (data.getMessage()) {
                case SIGN_UP_OK:
                    break;
                case SIGN_UP_ERROR_USER:
                    throw new ExistUserException();
                case CONNECTION_ERROR:
                    throw new ConnectionErrorException();
                default:
                    System.out.println("Otros");
            }
        } catch (IOException ex) {
            throw new ConnectionErrorException();
        } catch (ClassNotFoundException ex) {
            throw new ConnectionErrorException();
        }
    }

}
