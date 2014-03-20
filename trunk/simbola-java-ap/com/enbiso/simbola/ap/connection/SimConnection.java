package com.enbiso.simbola.ap.connection;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by farflk on 3/20/14.
 */
public class SimConnection {
    private String connectionUrl;
    private String username;
    private String password;
    private SimSession session;

    public SimConnection(String connectionUrl, String username, String password) {
        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
        this.session = new SimSession("guest", "");
    }

    public void login(){

    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public SimSession getSession() {
        return session;
    }
}
