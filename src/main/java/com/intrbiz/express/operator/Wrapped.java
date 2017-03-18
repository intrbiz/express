package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;

public class Wrapped extends UnaryOperator
{

	public Wrapped()
	{
		super("Wrapped");
	}

	public Wrapped(Operator o)
	{
		this();
		this.setOperator(o);
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
		Operator op = this.getOperator();
		return op.get(context, source);
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
		Operator op = this.getOperator();
		op.set(context, value, source);
	}

    @Override
    public Converter<?> getConverter(ExpressContext context, Object source) throws ExpressException
    {
        Operator op = this.getOperator();
        return op.getConverter(context, source);
    }

    @Override
    public Validator<?> getValidator(ExpressContext context, Object source) throws ExpressException
    {
        Operator op = this.getOperator();
        return op.getValidator(context, source);
    }

    @Override
	public String toString()
	{
		return "#{" + this.getOperator().toString() + "}";
	}
}
