package com.enbiso.simbola.ap.connection;

import com.enbiso.simbola.ap.service.SimServiceClient;
import com.enbiso.simbola.ap.service.SimServiceResponse;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

/**
 * Simbola Connection class
 * Created by Faraj on 3/20/14.
 */
public class SimConnection {
    /**
     * Constant of Guest Username
     */
    static final String GUEST_USERNAME = "guest";
    /**
     * Connection URL, the base URL of the Simbola Application
     */
    private String connectionUrl;
    /**
     * Username
     */
    private String username;
    /**
     * Password
     */
    private String password;
    /**
     * Session instance
     */
    private SimSession session;

    /**
     * Sim Connection constructor
     */
    public SimConnection() {
        this.session = new SimSession(GUEST_USERNAME, "");        
    }

    /**
     * Sim Connection constructor
     * @param connectionUrl Connection URL
     */
    public SimConnection(String connectionUrl){
        this();
        this.setConnectionUrl(connectionUrl);
    }

    /**
     * Sim Connection constructor
     * @param connectionUrl Connection URL
     * @param username Username
     * @param password Password
     */
    public SimConnection(String connectionUrl, String username, String password) {        
        this(connectionUrl);
        setLoginCredentials(username, password);
    }

    /**
     * Set Connection URL
     * @param connectionUrl Connection URL
     */
    public final void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }
    
    /**
     * Set Login details
     * @param username Username
     * @param password Password
     */
    public final void setLoginCredentials(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Check if logged
     * @return Is Logged
     */
    public Boolean isLogged(){
        return !this.session.getUsername().equals(GUEST_USERNAME);
    }
    
    /**
     * Login with the details provided
     * @return Is Logged
     * @throws java.io.IOException
     */
    public boolean login() throws JsonSyntaxException, IOException{
        SimServiceClient client = new SimServiceClient(this, "system", "auth", "login");
        client.addParam("username", username);
        client.addParam("password", password);
        SimServiceResponse response = client.execute();
        if(response.checkStatusOk()){
            this.session = new SimSession(
                    response.getResponse("username", String.class),
                    response.getResponse("skey", String.class));
            return true;
        }else{
            return false;
        }
    }

    public boolean logout() throws JsonSyntaxException, IOException{
        SimServiceClient client = new SimServiceClient(this, "system", "auth", "logout");
        SimServiceResponse response = client.execute();
        if(response.checkStatusOk()){
            this.session = new SimSession(GUEST_USERNAME, "");
            return true;
        }else{
            return false;
        }
    }

    /**
     * Returns the connection URL
     * @return Connection URL
     */
    public String getConnectionUrl() {
        return connectionUrl;
    }

    /**
     * Returns the session instance
     * @return Session instance
     */
    public SimSession getSession() {
        return session;
    }

    /**
     * Returns username
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns password
     * @return Password
     */
    public String getPassword() {
        return password;
    }
}
