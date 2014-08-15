package com.intrbiz.express.tests.model;

import java.util.HashMap;
import java.util.Map;

public class SomeBean
{
    private String someProperty = "Has a value";
    
    private Map<String, String> someMap = new HashMap<String, String>();
    
    private boolean someBool = true;
    
    public SomeBean()
    {
        super();
        this.someMap.put("name_a", "Value A");
        this.someMap.put("name b", "Value B");
    }

    public String getSomeProperty()
    {
        return someProperty;
    }

    public void setSomeProperty(String someProperty)
    {
        this.someProperty = someProperty;
    }
    
    public String doSomeThing()
    {
        return "Done";
    }

    public Map<String, String> getSomeMap()
    {
        return someMap;
    }

    public void setSomeMap(Map<String, String> someMap)
    {
        this.someMap = someMap;
    }

    public boolean isSomeBool()
    {
        return someBool;
    }

    public void setSomeBool(boolean someBool)
    {
        this.someBool = someBool;
    }
}
