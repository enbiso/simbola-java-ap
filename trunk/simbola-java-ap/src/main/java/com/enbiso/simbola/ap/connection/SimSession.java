package com.enbiso.simbola.ap.connection;

/**
 * Simbola Session class
 * Created by Faraj on 3/20/14.
 */
public class SimSession {
    /**
     * Username
     */
    private String username;
    /**
     * Session key
     */
    private String skey;

    /**
     * Create a simbola session instance
     * @param username Username
     * @param skey Session Key
     */
    public SimSession(String username, String skey) {
        this.username = username;
        this.skey = skey;
    }

    /**
     * Returns session key
     * @return Session Key
     */
    public String getSkey() {
        return skey;
    }

    /**
     * Returns username
     * @return Username
     */
    public String getUsername() {
        return username;
    }
}
