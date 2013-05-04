package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;


public abstract class Statement
{
    public abstract void execute(ExpressContext ctx, Object source) throws ExpressException;
    
    public abstract String toString(String padding);
    
    public String toString()
    {
        return this.toString("");
    }
}
