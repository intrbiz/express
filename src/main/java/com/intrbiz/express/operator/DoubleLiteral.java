package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.ConverterDouble;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.ValidatorDouble;

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
	public Converter getConverter(ELContext context, Object source) throws ELException
	{
		return new ConverterDouble();
	}

	@Override
	public Validator getValidator(ELContext context, Object source) throws ELException
	{
		return new ValidatorDouble();
	}
}
