package com.intrbiz.express.operator;

import java.lang.reflect.Method;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.dynamic.DynamicEntity;
import com.intrbiz.express.dynamic.EntityProxy;
import com.intrbiz.express.util.ELReflectUtil;
import com.intrbiz.express.value.ValueExpression;
import com.intrbiz.validator.Validator;

public class PropertyInvoke extends Operator
{
	private Operator left;
	
	private String property;
	
	private volatile MethodCache getterCache;
	
	private volatile MethodCache setterCache;

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

	protected Object resolveEntity(ExpressContext context, Object source) throws ExpressException
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

	protected String resolveProperty(ExpressContext ctx, Object src) throws ExpressException
	{
		return this.getProperty();
	}
	
	protected Method getGetter(Class<?> onClass, String property, ExpressContext context)
	{
	    MethodCache cache = this.getterCache;
	    if (cache == null || cache.type != onClass)
	    {
	        cache = null;
	        Method method = ELReflectUtil.findGetter(onClass, property);
	        if (method != null)
	        {
	            method.setAccessible(true);
	            cache = new MethodCache(method, onClass);
	            if (context.isCaching()) this.getterCache = cache;
	        }
	    }
	    return cache == null ? null : cache.method;
	}
	
	protected Method getSetter(Class<?> onClass, String property, ExpressContext context)
    {
        MethodCache cache = this.setterCache;
        if (cache == null || cache.type != onClass)
        {
            cache = null;
            Method method = ELReflectUtil.findSetter(onClass, property);
            if (method != null)
            {
                method.setAccessible(true);
                cache = new MethodCache(method, onClass);
                if (context.isCaching()) this.setterCache = cache;
            }
        }
        return cache == null ? null : cache.method;
    }

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
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
			Method getter = this.getGetter(entity.getClass(), property, context);
			if (getter != null)
			    return ELReflectUtil.invokeMethod(getter, entity);
		}
		return null;
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
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
			Method setter = this.getSetter(entity.getClass(), property, context);
			if (setter != null)
			    ELReflectUtil.invokeMethod(setter, entity, value);
		}
	}

	@Override
	public Converter<?> getConverter(ExpressContext context, Object source) throws ExpressException
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
			Method getter = this.getGetter(entity.getClass(), property, context);
			if (getter != null)
			{
    			try
    			{
    				return Converter.fromMethod(getter);
    			}
    			catch (Exception e)
    			{
    				throw new ExpressException("Could not get converter");
    			}
			}
		}
		return null;
	}

	@Override
	public Validator<?> getValidator(ExpressContext context, Object source) throws ExpressException
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
			Method getter = this.getGetter(entity.getClass(), property, context);
			if (getter != null)
			{
    			try
    			{
    				return Validator.fromMethod(getter);
    			}
    			catch (Exception e)
    			{
    				throw new ExpressException("Could not get validator");
    			}
			}
		}
		return null;
	}
	
	private static class MethodCache
	{
	    public final Method method;
	    
	    public final Class<?> type;
	    
	    public MethodCache(Method method, Class<?> type)
	    {
	        this.method = method;
	        this.type = type;
	    }
	}
}
