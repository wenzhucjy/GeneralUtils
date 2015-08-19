package com.github.mysite.web.wenzhu.common.initbinder;

import org.springframework.beans.propertyeditors.PropertiesEditor;

public class IntegerEditor extends PropertiesEditor {    
    @Override    
    public void setAsText(String text) throws IllegalArgumentException {    
        if (text == null || text.equals("")) {    
            text = "0";    
        }    
        setValue(Integer.parseInt(text));    
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }    
}   