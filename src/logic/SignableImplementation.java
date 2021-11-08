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
import java.net.ConnectException;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alain Lozano, Ilia Consuegra
 */
public class SignableImplementation implements Signable {

    private final static int PORT = Integer.valueOf(ResourceBundle.getBundle("dataModel.ServerConfiguration").getString("Port"));
    private final static String IP = ResourceBundle.getBundle("dataModel.ServerConfiguration").getString("ServerHost");

    private DataEncapsulation data = null;

    @Override
    public User signIn(User user) throws UserNotExistException, IncorrectPasswordException, ConnectionErrorException {
        Socket sc;
        try {
            sc = new Socket(IP, PORT);
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(sc.getOutputStream());
            data = new DataEncapsulation();
            data.setUser(user);
            data.setMessage(MessageEnum.SIGN_IN);
            oos.writeObject(data);

            ObjectInputStream ois = null;
            ois = new ObjectInputStream(sc.getInputStream());
            data = (DataEncapsulation) ois.readObject();
            user = data.getUser();
            int message = data.getMessage().ordinal();
            oos.close();
            ois.close();
            sc.close();
            switch (data.getMessage()) {
                case SIGN_IN_OK:
                    return user;
                case SIGN_IN_ERROR_USER:
                    throw new UserNotExistException();                  
                case SIGN_IN_ERROR_PASSWORD:
                    throw new IncorrectPasswordException();
                default:
                    throw new ConnectionErrorException();
            }
        } catch (IOException ex) {
            throw new ConnectionErrorException();
        } catch (ClassNotFoundException ex) {
           throw new ConnectionErrorException();
        }

    }

    @Override
    public void signUp(User user) throws ExistUserException, ConnectionErrorException, Exception {
        Socket sc;
        try {
            sc = new Socket(IP, PORT);
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(sc.getOutputStream());
            data = new DataEncapsulation();
            data.setUser(user);
            data.setMessage(MessageEnum.SIGN_UP);
            oos.writeObject(data);

            ObjectInputStream ois = null;
            ois = new ObjectInputStream(sc.getInputStream());
            data = (DataEncapsulation) ois.readObject();
            user = data.getUser();
            oos.close();
            ois.close();
            sc.close();
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
