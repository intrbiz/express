package com.intrbiz.express.operator;

import java.lang.reflect.Method;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.ConverterManager;
import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.express.dynamic.DynamicEntity;
import com.intrbiz.express.dynamic.EntityProxy;
import com.intrbiz.express.util.ELUtil;
import com.intrbiz.express.value.ValueExpression;
import com.intrbiz.validator.ValidationManager;
import com.intrbiz.validator.Validator;

public class PropertyInvoke extends Operator
{

	private Operator left;
	private String property;

	public PropertyInvoke(Operator e, String property)
	{
		super("PropertyInvoke");
		this.setLeft(e);
		this.setProperty(property);
	}

	public Operator getLeft()
	{
		return left;
	}

	public void setLeft(Operator left)
	{
		this.left = left;
	}

	public String getProperty()
	{
		return property;
	}

	public void setProperty(String property)
	{
		this.property = property;
	}

	public String toString()
	{
		return this.getLeft() + "." + this.getProperty();
	}

	protected Object resolveEntity(ELContext context, Object source) throws ELException
	{
		Object obj = this.getLeft().get(context, source);
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
		return obj;
	}

	protected String resolveProperty(ELContext ctx, Object src) throws ELException
	{
		return this.getProperty();
	}

	@Override
	public Object get(ELContext context, Object source) throws ELException
	{
		// evaluate the left hand side to get the entity value
		Object entity = this.resolveEntity(context, source);
		// eval the right hand side
		String property = this.resolveProperty(context, source);
		// get the property
		if (entity == null || property == null) return null;
		if (entity instanceof DynamicEntity)
		{
			return ((DynamicEntity) entity).get(property, context, source);
		}
		else
		{
			Method getter = ELUtil.findGetter(entity, property);
			if (getter == null)
				return null;
			try
			{
				return getter.invoke(entity, new Object[] {});
			}
			catch (Exception e)
			{
			}
		}
		return null;
	}

	@Override
	public void set(ELContext context, Object value, Object source) throws ELException
	{
		// evaluate the left hand side to get the entity value
		Object entity = this.resolveEntity(context, source);
		// eval the right hand side
		String property = this.resolveProperty(context, source);
		// get the property
		if (entity == null || property == null) return;
		if (entity instanceof DynamicEntity)
		{
			((DynamicEntity) entity).set(property, value, context, source);
		}
		else
		{
			Method setter = ELUtil.findSetter(entity, property);
			if (setter == null)
				return;
			try
			{
				setter.invoke(entity, new Object[] { value });
			}
			catch (Exception e)
			{
			}
		}
	}

	@Override
	public Converter getConverter(ELContext context, Object source) throws ELException
	{
		// evaluate the left hand side to get the entity value
		Object entity = this.resolveEntity(context, source);
		// eval the right hand side
		String property = this.resolveProperty(context, source);
		// get the property
		if (entity == null || property == null) return null;
		if (entity instanceof DynamicEntity)
		{
			return ((DynamicEntity) entity).getConverter(property, context, source);
		}
		else
		{
			Method getter = ELUtil.findGetter(entity, property);
			if (getter == null)
				return null;
			try
			{
				return ConverterManager.getConverter(getter);
			}
			catch (Exception e)
			{
				throw new ELException("Could not get converter");
			}
		}
	}

	@Override
	public Validator getValidator(ELContext context, Object source) throws ELException
	{
		// evaluate the left hand side to get the entity value
		Object entity = this.resolveEntity(context, source);
		// eval the right hand side
		String property = this.resolveProperty(context, source);
		// get the property
		if (entity == null || property == null) return null;
		if (entity instanceof DynamicEntity)
		{
			return ((DynamicEntity) entity).getValidator(property, context, source);
		}
		else
		{
			Method getter = ELUtil.findGetter(entity, property);
			if (getter == null)
				return null;
			try
			{
				return ValidationManager.getValidator(getter);
			}
			catch (Exception e)
			{
				throw new ELException("Could not get validator");
			}
		}
	}
}
