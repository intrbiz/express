package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.DoubleConverter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.DoubleValidator;

public class DoubleLiteral extends Literal
{

	private double value;

	public DoubleLiteral(double val)
	{
		super("DoubleLiteral");
		this.value = val;
	}

	public Double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public String toString()
	{
		return String.valueOf(this.getValue()) + (this.getValue() == null ? "" : "D");
	}

	@Override
	public Converter getConverter(ExpressContext context, Object source) throws ExpressException
	{
		return new DoubleConverter();
	}

	@Override
	public Validator getValidator(ExpressContext context, Object source) throws ExpressException
	{
		return new DoubleValidator();
	}
}
