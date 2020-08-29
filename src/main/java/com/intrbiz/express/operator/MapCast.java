package com.intrbiz.express.operator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class MapCast extends Function
{
    
    public MapCast()
    {
        super("map");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        Map<Object,Object> ret = new HashMap<>();
        Object k = null;
        for (Operator o : this.getParameters())
        {
            Object p = o.get(context,source);
            if (p instanceof Map)
            {
                ret.putAll((Map<?,?>) p);
            }
            else if (p instanceof Collection)
            {
                Object l = null;
                for (Object i : ((Collection<?>) p))
                {
                    if (l == null)
                    {
                        l = i;
                    }
                    else
                    {
                        ret.put(l, i);
                        l = null;
                    }
                }
            }
            else if (p instanceof Iterable)
            {
                Object l = null;
                for (Object i : ((Iterable<?>) p))
                {
                    if (l == null)
                    {
                        l = i;
                    } 
                    else
                    {
                        ret.put(l, i);
                        l = null;
                    }
                }
            }
            else if (p instanceof Object[])
            {
                Object l = null;
                for (Object i : ((Object[]) p))
                {
                    if (l == null) 
                    {
                        l = i;
                    }
                    else
                    {
                        ret.put(l, i);
                        l = null;
                    }
                }
            }
            else
            {
                if (k == null)
                {
                    k = p;
                } 
                else
                {
                    ret.put(k, p);
                    k = null;
                }
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
