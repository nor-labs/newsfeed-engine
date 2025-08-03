package com.learn.newsfeed.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.newsfeed.model.SqsMessage;
public class SqsUtil {


    public static SqsMessage messageToObject(String json){
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, SqsMessage.class);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}
