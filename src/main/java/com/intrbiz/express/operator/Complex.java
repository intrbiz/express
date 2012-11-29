package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class Complex extends UnaryOperator
{

	public Complex()
	{
		super("complex");
	}

	public Complex(Operator o)
	{
		this();
		this.setOperator(o);
	}

	@Override
	public Object get(ELContext context, Object source) throws ELException
	{
		Operator op = this.getOperator();
		return op.get(context, source);
	}

	@Override
	public void set(ELContext context, Object value, Object source) throws ELException
	{
		Operator op = this.getOperator();
		op.set(context, value, source);
	}

	@Override
	public String toString()
	{
		return "(" + this.getOperator().toString() + ")";
	}
}
