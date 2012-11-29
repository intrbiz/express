package com.intrbiz.express.operator;


public class NullLiteral extends Literal
{
	
	public NullLiteral()
	{
		super("NullLiteral");
	}

	public Object getValue()
	{
		return null;
	}
}
