package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;


public abstract class Literal extends Operator
{

	protected Literal(String name)
	{
		super(name);
	}
	
	public abstract Object getValue();
	
	public String toString()
	{
		return String.valueOf(this.getValue());
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
		return this.getValue();
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
	}
	
	@Override
	public boolean isConstant()
	{
	    return true;
	}
}
