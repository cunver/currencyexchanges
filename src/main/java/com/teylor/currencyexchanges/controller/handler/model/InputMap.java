package com.teylor.currencyexchanges.controller.handler.model;

import java.util.HashMap;

public class InputMap{

    private final HashMap<String, Object> inputMap = new HashMap<>();

    public InputMap put(String key, Object value){
        this.inputMap.put(key, value);
        return this;
    }

    public static InputMap create(){
        return new InputMap();
    }

    public Object get(String key){
        return this.inputMap.get(key);
    }

}
