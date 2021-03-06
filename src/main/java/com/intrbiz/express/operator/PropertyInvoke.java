package com.intrbiz.express.operator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.dynamic.DynamicEntity;
import com.intrbiz.express.dynamic.EntityProxy;
import com.intrbiz.express.security.Hidden;
import com.intrbiz.express.security.ReadOnly;
import com.intrbiz.express.util.ELReflectUtil;
import com.intrbiz.express.value.ValueExpression;
import com.intrbiz.validator.Validator;

public class PropertyInvoke extends Operator
{
	private Operator left;
	
	private String property;
	
	private volatile MethodCache getterCache;
	
	private volatile MethodCache setterCache;
	
	private volatile ConverterCache converterCache;
	
	private volatile ValidatorCache validatorCache;

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
	
	protected void checkEntityAccess(Object entity, boolean set) throws ExpressException
	{
	    if (entity instanceof Hidden)
	        throw new ExpressException("Illegal access to Hidden entity");
	    if (set && entity instanceof ReadOnly)
	        throw new ExpressException("Illegal access to ReadOnly entity");
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
	            if (context.allowSetAccessible()) method.setAccessible(true);
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
                if (context.allowSetAccessible()) method.setAccessible(true);
                cache = new MethodCache(method, onClass);
                if (context.isCaching()) this.setterCache = cache;
            }
        }
        return cache == null ? null : cache.method;
    }
	
	   protected Converter<?> getConverter(Method method)
	    {
	        ConverterCache cache = this.converterCache;
	        if (cache == null || cache.method != method)
	        {
	            cache = null;
	            try
	            {
	                cache = new ConverterCache(Converter.fromMethod(method), method);
	                this.converterCache = cache;
	            }
	            catch (Exception e)
	            {
	                throw new ExpressException("Could not get converter");
	            }
	        }
	        return cache == null ? null : cache.converter;
	    }
	    
	    protected Validator<?> getValidator(Method method)
	    {
	        ValidatorCache cache = this.validatorCache;
	        if (cache == null || cache.method != method)
	        {
	            cache = null;
	            try
	            {
	                cache = new ValidatorCache(Validator.fromMethod(method), method);
	                this.validatorCache = cache;
	            }
	            catch (Exception e)
	            {
	                throw new ExpressException("Could not get validator");
	            }
	        }
	        return cache == null ? null : cache.validator;
	    }

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
	    context.checkOp();
	    
		// evaluate the left hand side to get the entity value
		Object entity = this.resolveEntity(context, source);
		if (entity == null) return null;
		this.checkEntityAccess(entity, false);
		// eval the right hand side
		String property = this.resolveProperty(context, source);
		if (property == null) return null;
		// helper for array length
		if ("length".equals(property))
		{
		    if (entity instanceof Object[])
		        return ((Object[]) entity).length;
		    else if (entity instanceof Collection)
		        return ((Collection) entity).size();
            else if (entity instanceof Map)
                return ((Map) entity).size();
		}
		// get the property
		if (entity instanceof DynamicEntity)
		{
			return ((DynamicEntity) entity).get(property, context, source);
		}
		else if (entity instanceof Map)
		{
		    return ((Map<?, ?>) entity).get(property);
		}
		else
		{
			Method getter = this.getGetter(entity.getClass(), property, context);
			if (getter != null)
			    return ELReflectUtil.invokeMethod(context, getter, entity);
		}
		return null;
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
		// evaluate the left hand side to get the entity value
		Object entity = this.resolveEntity(context, source);
		if (entity == null) return;
		this.checkEntityAccess(entity, true);
		// eval the right hand side
		String property = this.resolveProperty(context, source);
		if (property == null) return;
		// get the property
		if (entity instanceof DynamicEntity)
		{
			((DynamicEntity) entity).set(property, value, context, source);
		}
		else
		{
			Method setter = this.getSetter(entity.getClass(), property, context);
			if (setter != null)
			    ELReflectUtil.invokeMethod(context, setter, entity, value);
		}
	}

	@Override
	public Converter<?> getConverter(ExpressContext context, Object source) throws ExpressException
	{
		// evaluate the left hand side to get the entity value
		Object entity = this.resolveEntity(context, source);
		if (entity == null) return null;
		this.checkEntityAccess(entity, false);
		// eval the right hand side
		String property = this.resolveProperty(context, source);
		if (property == null) return null;
		// get the property
		if (entity instanceof DynamicEntity)
		{
			return ((DynamicEntity) entity).getConverter(property, context, source);
		}
		else
		{
			Method getter = this.getGetter(entity.getClass(), property, context);
			if (getter != null)
			    return this.getConverter(getter);
		}
		return null;
	}

	@Override
	public Validator<?> getValidator(ExpressContext context, Object source) throws ExpressException
	{
		// evaluate the left hand side to get the entity value
		Object entity = this.resolveEntity(context, source);
		if (entity == null) return null;
		this.checkEntityAccess(entity, false);
		// eval the right hand side
		String property = this.resolveProperty(context, source);
		if (property == null) return null;
		// get the property
		if (entity instanceof DynamicEntity)
		{
			return ((DynamicEntity) entity).getValidator(property, context, source);
		}
		else
		{
			Method getter = this.getGetter(entity.getClass(), property, context);
			if (getter != null)
			    return this.getValidator(getter);
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
	
	private static class ConverterCache
	{
	    public final Converter<?> converter;
	    
	    public final Method method;
	    
	    public ConverterCache(Converter<?> converter, Method method)
	    {
	        this.converter = converter;
	        this.method = method;
	    }
	}
	
	private static class ValidatorCache
    {
        public final Validator<?> validator;
        
        public final Method method;
        
        public ValidatorCache(Validator<?> validator, Method method)
        {
            this.validator = validator;
            this.method = method;
        }
    }
	
	@Override
    public boolean isConstant()
    {
        return false;
    }
}
