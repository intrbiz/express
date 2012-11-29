package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;


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
	public Object get(ELContext context, Object source) throws ELException
	{
		return this.getValue();
	}

	@Override
	public void set(ELContext context, Object value, Object source) throws ELException
	{
	}
}
