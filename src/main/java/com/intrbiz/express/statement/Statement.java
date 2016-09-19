package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;


public abstract class Statement
{    
    /**
     * Execute this statement
     * @param ctx
     * @param source
     * @throws ExpressException
     */
    public abstract void execute(ExpressContext ctx, Object source) throws ExpressException;
    
    public abstract String toString(String padding);
    
    public String toString()
    {
        return this.toString("");
    }
}
