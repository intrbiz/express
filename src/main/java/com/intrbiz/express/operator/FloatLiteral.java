package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.ConverterFloat;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.ValidatorFloat;

public class FloatLiteral extends Literal
{

	private float value;

	public FloatLiteral(float val)
	{
		super("FloatLiteral");
		this.value = val;
	}

	public Float getValue()
	{
		return value;
	}

	public void setValue(float value)
	{
		this.value = value;
	}

	@Override
	public Converter getConverter(ExpressContext context, Object source) throws ExpressException
	{
		return new ConverterFloat();
	}

	@Override
	public Validator getValidator(ExpressContext context, Object source) throws ExpressException
	{
		return new ValidatorFloat();
	}
}
