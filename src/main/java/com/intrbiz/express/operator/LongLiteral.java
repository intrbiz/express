package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.ConverterLong;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.ValidatorLong;

public class LongLiteral extends Literal
{

	private long value;

	public LongLiteral(long val)
	{
		super("LongLiteral");
		this.value = val;
	}

	public Long getValue()
	{
		return value;
	}

	public void setValue(long value)
	{
		this.value = value;
	}

	public String toString()
	{
		return String.valueOf(this.getValue()) + (this.getValue() == null ? "" : "L");
	}
	

	@Override
	public Converter getConverter(ELContext context, Object source) throws ELException
	{
		return new ConverterLong();
	}

	@Override
	public Validator getValidator(ELContext context, Object source) throws ELException
	{
		return new ValidatorLong();
	}
}
