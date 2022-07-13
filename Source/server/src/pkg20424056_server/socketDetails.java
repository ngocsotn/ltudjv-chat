/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20424056_server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author NGOC
 */
public class socketDetails {

    /**
     * @return the _currentUser
     */
    public String getCurrentUser() {
        return _currentUser;
    }

    /**
     * @param _currentUser the _currentUser to set
     */
    public void setCurrentUser(String _currentUser) {
        this._currentUser = _currentUser;
    }
    /**
     * @return the _socket
     */
    public Socket getSocket() {
        return _socket;
    }

    /**
     * @param _socket the _socket to set
     */
    public void setSocket(Socket _socket) {
        this._socket = _socket;
    }

    /**
     * @return the _fromClient
     */
    public BufferedReader getFromClient() {
        return _fromClient;
    }

    /**
     * @param _fromClient the _fromClient to set
     */
    public void setFromClient(BufferedReader _fromClient) {
        this._fromClient = _fromClient;
    }

    /**
     * @return the _toClient
     */
    public DataOutputStream getToClient() {
        return _toClient;
    }

    /**
     * @param _toClient the _toClient to set
     */
    public void setToClient(DataOutputStream _toClient) {
        this._toClient = _toClient;
    }
    
    public socketDetails(Socket _socketIn) throws IOException
    {
        _socket = _socketIn;
        _fromClient = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        _toClient = new DataOutputStream(_socket.getOutputStream());
    }
    
    public void sendMessage(String msg) throws IOException
    {
        _toClient.writeBytes(msg);
    }
    
    private Socket _socket;
    private BufferedReader _fromClient;
    private DataOutputStream _toClient;
    private String _currentUser = "";
    
}