package com.intrbiz.express.operator;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.intrbiz.converter.Converter;
import com.intrbiz.converter.ConverterManager;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.dynamic.DynamicEntity;
import com.intrbiz.express.util.ELUtil;
import com.intrbiz.validator.ValidationManager;
import com.intrbiz.validator.Validator;

public class ArrayInvoke extends BinaryOperator
{

	public ArrayInvoke(Operator e, Operator n)
	{
		super("ArrayInvoke");
		this.setLeft(e);
		this.setRight(n);
	}

	public String toString()
	{
		return this.getLeft() + "[" + this.getRight() + "]";
	}

	@SuppressWarnings("rawtypes")
    @Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
		// get the entity
		Object entity = this.getLeft().get(context, source);
		// get the prop
		Object prop = this.getRight().get(context, source);
		// evaluate
		if (entity == null || prop == null)
			return null;
		if (entity instanceof List<?> && prop instanceof Integer)
		{
			List list = (List) entity;
			try
			{
				return list.get((Integer) prop);
			}
			catch (Exception e)
			{
			}
		}
		else if (entity instanceof Object[] && prop instanceof Integer)
		{
			Object[] oa = (Object[]) entity;
			try
			{
				return oa[(Integer) prop];
			}
			catch (Exception e)
			{
			}
		}
		else if (entity instanceof Map<?, ?>)
		{
			Map map = (Map) entity;
			try
			{
				return map.get(prop);
			}
			catch (Exception e)
			{
			}
		}
		else if (entity instanceof DynamicEntity && prop instanceof String)
		{
			DynamicEntity de = (DynamicEntity) entity;
			de.get((String) prop, context, source);
		}
		else if (prop instanceof String)
		{
			Method getter = ELUtil.findGetter(entity, (String) prop);
			if (getter == null)
				return null;
			try
			{
				return getter.invoke(entity, new Object[]
				{});
			}
			catch (Exception e)
			{
			}
		}
		return null;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
		// get the entity
		Object entity = this.getLeft().get(context, source);
		// get the prop
		Object prop = this.getRight().get(context, source);
		// evaluate
		if (entity == null || prop == null)
			return;
		if (entity instanceof List<?> && prop instanceof Integer)
		{
			List list = (List) entity;
			try
			{
				list.set((Integer) prop, source);
			}
			catch (Exception e)
			{
			}
		}
		else if (entity instanceof Object[] && prop instanceof Integer)
		{
			Object[] oa = (Object[]) entity;
			try
			{
				oa[(Integer) prop] = value;
			}
			catch (Exception e)
			{
			}
		}
		else if (entity instanceof Map<?, ?>)
		{
			Map map = (Map) entity;
			try
			{
				map.put(prop, value);
			}
			catch (Exception e)
			{
			}
		}
		else if (entity instanceof DynamicEntity && prop instanceof String)
		{
			DynamicEntity de = (DynamicEntity) entity;
			de.set((String) prop, value, context, source);
		}
		else if (prop instanceof String)
		{
			Method setter = ELUtil.findSetter(entity, (String) prop);
			if (setter == null)
				return;
			try
			{
				setter.invoke(entity, new Object[]
				{ value });
			}
			catch (Exception e)
			{
			}
		}
	}

	@Override
	public Converter getConverter(ExpressContext context, Object source) throws ExpressException
	{
		// get the entity
		Object entity = this.getLeft().get(context, source);
		// get the prop
		Object prop = this.getRight().get(context, source);
		// evaluate
		if (entity == null || prop == null)
			return null;
		if (entity instanceof DynamicEntity && prop instanceof String)
		{
			DynamicEntity de = (DynamicEntity) entity;
			return de.getConverter((String) prop, context, source);
		}
		else if (prop instanceof String)
		{
			Method getter = ELUtil.findGetter(entity, (String) prop);
			if (getter == null)
				return null;
			try
			{
				return ConverterManager.getConverter(getter);
			}
			catch (Exception e)
			{
				throw new ExpressException("Could not get converter");
			}
		}
		return null;
	}

	@Override
	public Validator getValidator(ExpressContext context, Object source) throws ExpressException
	{
		// get the entity
		Object entity = this.getLeft().get(context, source);
		// get the prop
		Object prop = this.getRight().get(context, source);
		// evaluate
		if (entity == null || prop == null)
			return null;
		if (entity instanceof DynamicEntity && prop instanceof String)
		{
			DynamicEntity de = (DynamicEntity) entity;
			return de.getValidator((String) prop, context, source);
		}
		else if (prop instanceof String)
		{
			Method getter = ELUtil.findGetter(entity, (String) prop);
			if (getter == null)
				return null;
			try
			{
				return ValidationManager.getValidator(getter);
			}
			catch (Exception e)
			{
				throw new ExpressException("Could not get validator");
			}
		}
		return null;
	}
}
