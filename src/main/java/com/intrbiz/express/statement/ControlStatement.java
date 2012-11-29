package com.intrbiz.express.statement;


public abstract class ControlStatement extends Statement
{
    private final String name;
    
    public ControlStatement(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }    
}
