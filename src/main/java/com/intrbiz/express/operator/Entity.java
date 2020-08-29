package com.intrbiz.express.operator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.dynamic.EntityProxy;
import com.intrbiz.express.value.ValueExpression;
import com.intrbiz.validator.Validator;

public class Entity extends Literal
{
    private static final Map<String, Object> BUILTIN = new HashMap<>();
    
    static {
        BUILTIN.put("Arrays", Arrays.class);
        BUILTIN.put("Collections", Collections.class);
        BUILTIN.put("Collectors", Collectors.class);
        BUILTIN.put("Stream", Stream.class);
    }

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
	
	protected Object getEntity(ExpressContext context, Object source)
	{
	    String name = this.getValue();
	    // Try built ins
	    Object obj = BUILTIN.get(name);
	    // Go to the context
	    if (obj == null)
	    {
    	    obj = context.getEntity(name, source);
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
	    }
        return obj;
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
	    context.checkOp();
	    
	    return this.getEntity(context, source);
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
	    context.checkOp();
	    
		Object obj = this.getEntity(context, source);
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
	public Converter<?> getConverter(ExpressContext context, Object source) throws ExpressException
	{
	    Object obj = this.getEntity(context, source);
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
	public Validator<?> getValidator(ExpressContext context, Object source) throws ExpressException
	{
	    Object obj = this.getEntity(context, source);
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
	
	@Override
	public boolean isConstant()
	{
	    return false;
	}
}
