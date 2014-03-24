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
     * @param skey Session Key
     * @param username Username
     */
    public SimSession(String skey, String username) {
        this.skey = skey;
        this.username = username;
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
