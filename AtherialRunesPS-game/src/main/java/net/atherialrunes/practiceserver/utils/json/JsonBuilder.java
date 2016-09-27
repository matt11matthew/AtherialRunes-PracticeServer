package net.atherialrunes.practiceserver.utils.json;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew E on 9/27/2016 at 4:42 PM.
 */
public class JsonBuilder {

    private JsonObject json;

    public JsonBuilder(){
        initiateData();
    }

    public JsonBuilder(String key, Object value){
        initiateData();
        json.addProperty(key, value.toString());
    }

    public JsonBuilder(HashMap<String, Object> data){
        initiateData();
        for(Map.Entry<String, Object> x : data.entrySet()){
            json.addProperty(x.getKey(), x.getValue().toString());
        }
    }

    private void initiateData(){
        json = new JsonObject();
    }

    public JsonBuilder setData(String key, Object value){
        json.addProperty(key, value.toString());
        return this;
    }

    public JsonObject getJson(){
        return json;
    }

    @Override
    public String toString(){
        return json.toString();
    }
}