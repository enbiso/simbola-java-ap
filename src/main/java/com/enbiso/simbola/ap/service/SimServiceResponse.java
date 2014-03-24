/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.enbiso.simbola.ap.service;

import java.util.Map;

/**
 * Simbola Service Response
 * @author Faraj
 */
public class SimServiceResponse {
    public static final String HEADER = "header";
    public static final String HEADER_STATUS = "status";
    public static final String HEADER_STATUS_TEXT = "status_text";
    public static final String BODY = "body";
    public static final String BODY_RESPONSE = "response";
    public static final String BODY_MESSAGE = "message";
    public static final int STATUS_OK = 200;
    public static final int STATUS_ERROR = 500;
    public static final int STATUS_USER_ERROR = 201;
    public static final int STATUS_INVALID_SERVICE = 404;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_FOBIDDEN = 403;        
    
    /**
     * Header content
     */
    private final Map header;
    
    /**
     * Body content
     */
    private final Map body;

    /**
     * Create Service Response
     * @param response output Map response
     */
    public SimServiceResponse(Map response) {
        this.header = (Map) response.get(HEADER);
        this.body = (Map) response.get(BODY);
    }
    
    /**
     * Checks the status
     * @param status STATUS_*
     * @return 
     */
    public boolean checkStatus(int status){
        return Integer.valueOf(this.getHeader(HEADER_STATUS, String.class)) == status;
    }
    
    /**
     * Check response status is OK
     * @return 
     */
    public boolean checkStatusOk(){
        return this.checkStatus(STATUS_OK);
    }

    /**
     * Returns the whole Header
     * @return 
     */
    public Map getHeader() {
        return header;
    }

    /**
     * Returns the whole body
     * @return 
     */
    public Map getBody() {
        return body;
    }    
    
    /**
     * Returns the header content as object
     * @param name Header name
     * @return 
     */
    public Object getHeader(String name){
        return this.header.get(name);
    }
    
    /**
     * Returns the header content
     * @param <T> Parse Class
     * @param name Header name
     * @param type Parse Type
     * @return 
     */
    public <T> T getHeader(String name, Class<T> type){
        return (T)this.header.get(name);
    }
    
    /**
     * Returns the value in body
     * @param name Body name
     * @return 
     */
    public Object getBody(String name){
        return this.body.get(name);
    }
    
    /**
     * Returns the message
     * @return 
     */
    public String getMessage(){
        return (String) getBody(BODY_MESSAGE);
    }
    
    /**
     * Get all response
     * @return 
     */
    public Object getResponse(){
        return this.getBody(BODY_RESPONSE);
    }
    
    /**
     * Get all response
     * @param <T> Parse class
     * @param type Parse type
     * @return 
     */
    public <T> T getResponse(Class<T> type){
        return (T)this.getBody(BODY_RESPONSE);
    }
    
    /**
     * returns response as object
     * @param name Response name
     * @return 
     */
    public Object getResponse(String name){
        return this.getResponse(Map.class).get(name);
    }
    
    /**
     * Get Response
     * @param <T> Parse Type
     * @param name Response name
     * @param type Parse Class
     * @return 
     */
    public <T> T getResponse(String name, Class<T> type){
        return (T)this.getResponse(Map.class).get(name);
    }
}
