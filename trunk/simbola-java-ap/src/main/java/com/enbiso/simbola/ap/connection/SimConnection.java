package com.enbiso.simbola.ap.connection;

import com.enbiso.simbola.ap.service.SimServiceClient;
import com.enbiso.simbola.ap.service.SimServiceResponse;
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
    private final String connectionUrl;
    /**
     * Username
     */
    private final String username;
    /**
     * Password
     */
    private final String password;
    /**
     * Session instance
     */
    private SimSession session;

    /**
     * Sim Connection constructor
     * @param connectionUrl Connection URL
     * @param username Username
     * @param password Password
     */
    public SimConnection(String connectionUrl, String username, String password) {
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        this.session = new SimSession(GUEST_USERNAME, "");
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
     */
    public boolean login(){
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
}
