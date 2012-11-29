package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class NotEquals extends Equals
{

    public NotEquals()
    {
        super("!=");
    }
    
    public NotEquals(Operator l, Operator r)
    {
    	this();
    	this.setLeft(l);
    	this.setRight(r);
    }

    @Override
    public Boolean get(ELContext context,Object source) throws ELException
    {
    	return ! super.get(context, source);
    }
}
