package com.intrbiz.express.statement;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;


public abstract class Statement
{
    public abstract void execute(ELContext ctx, Object source) throws ELException;
    
    public abstract String toString(String padding);
    
    public String toString()
    {
        return this.toString("");
    }
}
