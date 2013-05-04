package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Print extends Function
{

	public Print()
	{
		super("print");
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
		Operator op = this.getParameters().get(0);
		Object val = op.get(context, source);
		System.out.println(val);
		return null;
	}
}
