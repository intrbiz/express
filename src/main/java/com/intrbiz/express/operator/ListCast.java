package com.intrbiz.express.operator;

import java.util.LinkedList;
import java.util.List;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class ListCast extends Function
{
    
    public ListCast()
    {
        super("list");
    }

    @Override
    public Object get(ELContext context, Object source) throws ELException
    {
        List<Object> ret = new LinkedList<Object>();
        for (Operator o : this.getParameters())
        {
            Object p = o.get(context,source);
            ret.add(p);
        }
        return ret ;
    }
    
    
}
