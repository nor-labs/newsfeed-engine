package com.learn.newsfeed.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.newsfeed.model.EventMessage;
public class SqsUtil {


    public static EventMessage messageToObject(String json){
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, EventMessage.class);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}
