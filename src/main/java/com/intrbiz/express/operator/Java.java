package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Java extends Function
{

    public Java()
    {
        super("java");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        try
        {
            if (this.getParameters().size() == 1)
            {
                String className = (String) this.getParameters().get(0).get(context, source);
                if (context.checkJavaAccess(className))
                {
                    return Class.forName(className);
                }
                else
                {
                    throw new ExpressException("Cannot load Java class: " + className);
                }
            }
        }
        catch (Exception e)
        {
            throw new ExpressException("Failed to load Java class", e);
        }
        return null;
    }

    @Override
    public void set(ExpressContext context, Object value,Object source) throws ExpressException
    {
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
