package com.enbiso.simbola.ap.connection;

/**
 * Created by farflk on 3/20/14.
 */
public class SimSession {
    private String username;
    private String skey;

    public SimSession(String skey, String username) {
        this.skey = skey;
        this.username = username;
    }

    public String getSkey() {
        return skey;
    }

    public String getUsername() {
        return username;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
