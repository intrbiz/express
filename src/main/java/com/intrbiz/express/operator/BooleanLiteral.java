package com.intrbiz.express.operator;


public class BooleanLiteral extends Literal
{

	private boolean value;

	public BooleanLiteral(boolean val)
	{
		super("BooleanLiteral");
		this.value = val;
	}

	public Boolean getValue()
	{
		return value;
	}

	public void setValue(boolean value)
	{
		this.value = value;
	}

	/*@Override
	public Converter getConverter(ELContext context, Object source) throws ELException
	{
		return new ConverterBoolean();
	}

	@Override
	public Validator getValidator(ELContext context, Object source) throws ELException
	{
		return new ValidatorBoolean();
	}*/
}
