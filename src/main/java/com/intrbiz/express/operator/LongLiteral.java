package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.converters.LongConverter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.validator.Validator;
import com.intrbiz.validator.validators.LongValidator;

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
	public Converter<?> getConverter(ExpressContext context, Object source) throws ExpressException
	{
		return new LongConverter();
	}

	@Override
	public Validator<?> getValidator(ExpressContext context, Object source) throws ExpressException
	{
		return new LongValidator();
	}
}
