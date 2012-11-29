package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class If extends Function
{

    public If()
    {
        super("if");
    }

    @Override
    public Object get(ELContext context, Object source) throws ELException
    {
        Operator ev = this.getParameters().get(0);
        Operator tr = this.getParameters().get(1);
        Operator fl = this.getParameters().get(2);
        
        Object evo = ev.get(context,source);
        
        if (evo instanceof Boolean && ((Boolean)evo) == true )
        {
            return tr.get(context,source);
        }
        else
        {
            return fl.get(context,source);
        }
    }
    
    
    
}
