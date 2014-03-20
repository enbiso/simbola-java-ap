package com.enbiso.simbola.ap.service;

import android.util.ArrayMap;

import com.enbiso.simbola.ap.connection.SimConnection;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by farflk on 3/20/14.
 */
public class SimServiceClient {
    private SimConnection connection;
    private String module;
    private String lu;
    private String action;
    private Map<String, Object> params;
    private HttpClient httpClient;

    public SimServiceClient(SimConnection connection, String module, String lu, String action) {
        this.connection = connection;
        this.module = module;
        this.lu = lu;
        this.action = action;
        this.params = new ArrayMap<String, Object>();
        this.httpClient = new DefaultHttpClient();
    }

    private String getServiceUrl(){
        return this.connection.getConnectionUrl() + "/service_api";
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addParam(String key, Object value){
        this.params.put(key, value);
    }

    public Map<String, Object> execute(){
        Gson gson = new Gson();
        HttpPost request = new HttpPost(getServiceUrl());
        String jsonParams = gson.toJson(params);
        try {
            StringEntity se = new StringEntity(jsonParams);
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(se);
            HttpResponse response = httpClient.execute(request);
            InputStream stream = response.getEntity().getContent();
            String output = convertInputStreamToString(stream);
            return gson.fromJson(output, Map.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
