package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.IntegerConverter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.IntegerValidator;


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
	public Converter getConverter(ExpressContext context, Object source) throws ExpressException
	{
		return new IntegerConverter();
	}

	@Override
	public Validator getValidator(ExpressContext context, Object source) throws ExpressException
	{
		return new IntegerValidator();
	}
}
