package com.intrbiz.express.operator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class ListCast extends Function
{
    
    public ListCast()
    {
        super("list");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        List<Object> ret = new LinkedList<Object>();
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
