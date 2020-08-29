package com.intrbiz.express.operator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class SetCast extends Function
{
    
    public SetCast()
    {
        super("set");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        Set<Object> ret = new HashSet<Object>();
        for (Operator o : this.getParameters())
        {
            Object p = o.get(context,source);
            if (p instanceof Collection)
            {
                ret.addAll((Collection<?>) p);
            }
            else if (p instanceof Iterable)
            {
                for (Object i : ((Iterable<?>) p))
                {
                    ret.add(i);
                }
            }
            else if (p instanceof Object[])
            {
                Collections.addAll(ret, (Object[]) p);
            }
            else
            {
                ret.add(p);
            }
        }
        return ret ;
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
