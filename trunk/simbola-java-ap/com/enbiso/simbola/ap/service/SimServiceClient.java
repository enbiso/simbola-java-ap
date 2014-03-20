package com.enbiso.simbola.ap.service;

import com.enbiso.simbola.ap.connection.SimConnection;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * Created by farflk on 3/20/14.
 */
public class SimServiceClient {
    private SimConnection connection;
    private String module;
    private String service;
    private String action;
    private Map<String, String> params;
    private HttpClient httpClient;

    public SimServiceClient(SimConnection connection, String module, String service, String action) {
        this.connection = connection;
        this.module = module;
        this.service = service;
        this.action = action;
        this.params = new HashMap<String, String>();
        this.httpClient = HttpClients.createDefault();
    }

    private String getServiceUrl(){
        return this.connection.getConnectionUrl() + "/service_api";
    }

    public void addParam(String key, String value){
        this.params.put(key, value);
    }

    public Map<String, Object> execute(){
        Gson gson = new Gson();
        List<BasicNameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("module", module));
        postParameters.add(new BasicNameValuePair("service", service));
        postParameters.add(new BasicNameValuePair("action", action));
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().replace(".", "][");            
            String value = entry.getValue();
            postParameters.add(new BasicNameValuePair("params[" + key + "]", value));        
        }
        try {         
            HttpPost request = new HttpPost(getServiceUrl());                                    
            request.setEntity(new UrlEncodedFormEntity(postParameters));
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
