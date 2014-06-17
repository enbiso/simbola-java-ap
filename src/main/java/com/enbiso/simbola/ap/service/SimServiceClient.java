package com.enbiso.simbola.ap.service;

import com.enbiso.simbola.ap.connection.SimConnection;
import com.enbiso.simbola.ap.connection.SimSession;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * Simbola Service Client
 * Created by Faraj on 3/20/14.
 */
public class SimServiceClient {
    /**
     * Service API url
     */
    private String serviceApi = "service_api";
    /**
     * Simbola Connection
     */
    private final SimConnection connection;
    /**
     * Service Module name
     */
    private String module;
    /**
     * Service Service name
     */
    private String service;
    /**
     * Service Action name
     */
    private String action;
    /**
     * Service arguments
     */
    private Map<String, String> params;
    /**
     * HTTP Client instance
     */
    private static HttpClient httpClient;

    /**
     * Construct a Simbola service client with service source
     * @param connection Simbola Connection
     * @param module Module name
     * @param service Service name
     * @param action Action name
     */
    public SimServiceClient(SimConnection connection, String module, String service, String action) {
        this(connection);
        this.newSource(module, service, action);
    }
    
    /**
     * Construct a Simbola service client
     * @param connection Simbola Connection
     */
    public SimServiceClient(SimConnection connection){
        this.connection = connection;
        if(httpClient == null){
            httpClient = new DefaultHttpClient();
        }
    }
    
    /**
     * Setup new service source
     * @param module Module name
     * @param service Service name
     * @param action Action name
     */
    public final void newSource(String module, String service, String action){
        this.module = module;
        this.service = service;
        this.action = action;
        this.params = new HashMap();
    }

    /**
     * returns the service URL
     * @return Service URL
     */
    private String getServiceUrl(){
        return this.connection.getConnectionUrl() + "/" + serviceApi;
    }

    /**
     * Set Service API URL
     * Default - service_api
     * @param serviceApi Service API URL
     */
    public void setServiceApi(String serviceApi) {
        this.serviceApi = serviceApi;
    }
    
    /**
     * Add service arguments
     * @param key Key 'name' - for flat keys, 'customer.name' - For array keys
     * @param value Value
     */
    public void addParam(String key, String value){
        this.params.put(key, value);
    }

    /**
     * Execute the service call
     * @return Simbola Response
     */
    public SimServiceResponse execute() throws JsonSyntaxException, IOException{
        Gson gson = new Gson();
        List<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
        //Service source info
        postParameters.add(new BasicNameValuePair("module", module));
        postParameters.add(new BasicNameValuePair("service", service));
        postParameters.add(new BasicNameValuePair("action", action));
        //Auth information
        SimSession session = this.connection.getSession();
        postParameters.add(new BasicNameValuePair("auth[username]", session.getUsername()));
        postParameters.add(new BasicNameValuePair("auth[skey]", session.getSkey()));
        //Param information
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().replace(".", "][");            
            String value = entry.getValue();
            postParameters.add(new BasicNameValuePair("params[" + key + "]", value));        
        }
        HttpPost request = new HttpPost(getServiceUrl());
        request.setEntity(new UrlEncodedFormEntity(postParameters));
        HttpResponse response = httpClient.execute(request);
        InputStream stream = response.getEntity().getContent();
        String output = convertInputStreamToString(stream);
        Map outputMap = gson.fromJson(output, Map.class);
        return new SimServiceResponse(outputMap);
    }

    /**
     * Read the whole String content from the reader
     * @param inputStream input stream
     * @return String output
     * @throws IOException 
     */
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
