package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class Print extends Function
{

	public Print()
	{
		super("print");
	}

	@Override
	public Object get(ELContext context, Object source) throws ELException
	{
		Operator op = this.getParameters().get(0);
		Object val = op.get(context, source);
		System.out.println(val);
		return null;
	}
}
