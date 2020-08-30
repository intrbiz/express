package com.intrbiz.express.operator;

import java.util.Objects;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Var extends Operator
{
    private final String value;

	public Var(String value)
	{
		super("var");
		this.value = Objects.requireNonNull(value);
	}
	
	public String getVarName()
	{
	    return this.value;
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
	    return null;
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
	    context.declareEntity(this.value, value, source);
	}

    @Override
    public String toString()
    {
        return "var " + this.value;
    }
}
