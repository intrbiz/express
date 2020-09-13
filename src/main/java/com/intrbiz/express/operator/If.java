package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class If extends Function
{

    public If()
    {
        super("if");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        Operator ev = this.getParameters().get(0);
        Operator tr = this.getParameters().get(1);
        Operator fl = this.getParameters().get(2);
        
        Object evo = ev.get(context,source);
        
        if (evo instanceof Boolean ? ((Boolean)evo).booleanValue() : evo != null)
        {
            return tr.get(context,source);
        }
        else
        {
            return fl.get(context,source);
        }
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
