package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

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
    public Boolean get(ExpressContext context,Object source) throws ExpressException
    {
    	return ! super.get(context, source);
    }
}
