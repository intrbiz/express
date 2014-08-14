package com.intrbiz.express.tests.model;

public class SomeBean
{
    private String someProperty = "Has a value";
    
    public SomeBean()
    {
        super();
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
}
