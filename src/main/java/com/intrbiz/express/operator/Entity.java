package com.intrbiz.express.operator;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.dynamic.EntityProxy;
import com.intrbiz.express.value.ValueExpression;
import com.intrbiz.validator.Validator;

public class Entity extends Literal
{

	private String value;

	public Entity(String name)
	{
		super("EntityName");
		this.value = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
		Object obj = context.getEntity(this.getValue(), source);
		// Allow for expression chaining
		// Allow for entites to be proxied
		if (obj instanceof ValueExpression)
		{
			return ((ValueExpression) obj).get(context, source);
		}
		else if (obj instanceof Operator)
		{
			return ((Operator) obj).get(context, source);
		}
		else if (obj instanceof EntityProxy)
		{
			return ((EntityProxy) obj).getEntity(context, source);
		}
		else if (obj instanceof ExpressContext)
		{
			return ((ExpressContext) obj).getEntity(this.getValue(), source);
		}
		return obj;
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
		Object obj = context.getEntity(this.getValue(), source);
		// Allow for chaining of expressions
		if (obj instanceof ValueExpression)
		{
			((ValueExpression) obj).set(context, value, source);
		}
		else if (obj instanceof Operator)
		{
			((Operator) obj).set(context, value,source);
		}
		// create the entity within the context
		else
		{
		    context.setEntity(this.getValue(), value, source);
		}
	}

	@Override
	public Converter getConverter(ExpressContext context, Object source) throws ExpressException
	{
		Object obj = context.getEntity(this.getValue(), source);
		// Allow for chaining of expressions
		if (obj instanceof ValueExpression)
		{
			return ((ValueExpression) obj).getConverter(context, source);
		}
		else if (obj instanceof Operator)
		{
			return ((Operator) obj).getConverter(context, source);
		}
		return null;
	}

	@Override
	public Validator getValidator(ExpressContext context, Object source) throws ExpressException
	{
		Object obj = context.getEntity(this.getValue(), source);
		// Allow for chaining of expressions
		if (obj instanceof ValueExpression)
		{
			return ((ValueExpression) obj).getValidator(context, source);
		}
		else if (obj instanceof Operator)
		{
			return ((Operator) obj).getValidator(context, source);
		}
		return null;		
	}
}
