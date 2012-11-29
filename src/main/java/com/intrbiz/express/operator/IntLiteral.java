package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.ConverterInteger;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.ValidatorInteger;


public class IntLiteral extends Literal
{
	
	private int value;
	
	public IntLiteral(int val)
	{
		super("IntLiteral");
		this.value = val;
	}

	public Integer getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}
	

	@Override
	public Converter getConverter(ELContext context, Object source) throws ELException
	{
		return new ConverterInteger();
	}

	@Override
	public Validator getValidator(ELContext context, Object source) throws ELException
	{
		return new ValidatorInteger();
	}
}
