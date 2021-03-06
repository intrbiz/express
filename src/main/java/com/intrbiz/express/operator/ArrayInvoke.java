package com.intrbiz.express.operator;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.intrbiz.converter.Converter;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.dynamic.DynamicEntity;
import com.intrbiz.express.security.Hidden;
import com.intrbiz.express.security.ReadOnly;
import com.intrbiz.express.util.ELReflectUtil;
import com.intrbiz.validator.Validator;

public class ArrayInvoke extends BinaryOperator
{
    private volatile MethodCache getterCache;
    
    private volatile MethodCache setterCache;

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
    
    protected void checkEntityAccess(Object entity, boolean set) throws ExpressException
    {
        if (entity instanceof Hidden)
            throw new ExpressException("Illegal access to Hidden entity");
        if (set && entity instanceof ReadOnly)
            throw new ExpressException("Illegal access to ReadOnly entity");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        // get the entity
        Object entity = this.getLeft().get(context, source);
        if (entity == null) return null;
        this.checkEntityAccess(entity, false);
        // get the prop
        Object prop = this.getRight().get(context, source);
        if (prop == null) return null;
        // evaluate
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
            return de.get((String) prop, context, source);
        }
        else if (prop instanceof String)
        {
            Method getter = this.getGetter(entity.getClass(), (String) prop, context);
            if (getter != null)
                return ELReflectUtil.invokeMethod(context, getter, entity);
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void set(ExpressContext context, Object value, Object source) throws ExpressException
    {
        // get the entity
        Object entity = this.getLeft().get(context, source);
        if (entity == null) return;
        this.checkEntityAccess(entity, true);
        // get the prop
        Object prop = this.getRight().get(context, source);
        // evaluate
        if (prop == null) return;
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
            Method setter = this.getSetter(entity.getClass(), (String) prop, context);
            if (setter != null)
                ELReflectUtil.invokeMethod(context, setter, entity, value);
        }
    }

    @Override
    public Converter<?> getConverter(ExpressContext context, Object source) throws ExpressException
    {
        // get the entity
        Object entity = this.getLeft().get(context, source);
        if (entity == null) return null;
        this.checkEntityAccess(entity, false);
        // get the prop
        Object prop = this.getRight().get(context, source);
        if (prop == null) return null;
        // evaluate
        if (entity instanceof DynamicEntity && prop instanceof String)
        {
            DynamicEntity de = (DynamicEntity) entity;
            return de.getConverter((String) prop, context, source);
        }
        else if (prop instanceof String)
        {
            Method getter = this.getGetter(entity.getClass(), (String) prop, context);
            if (getter == null) return null;
            try
            {
                return Converter.fromMethod(getter);
            }
            catch (Exception e)
            {
                throw new ExpressException("Could not get converter");
            }
        }
        return null;
    }

    @Override
    public Validator<?> getValidator(ExpressContext context, Object source) throws ExpressException
    {
        // get the entity
        Object entity = this.getLeft().get(context, source);
        if (entity == null) return null;
        this.checkEntityAccess(entity, false);
        // get the prop
        Object prop = this.getRight().get(context, source);
        if (prop == null) return null;
        // evaluate
        if (entity instanceof DynamicEntity && prop instanceof String)
        {
            DynamicEntity de = (DynamicEntity) entity;
            return de.getValidator((String) prop, context, source);
        }
        else if (prop instanceof String)
        {
            Method getter = this.getGetter(entity.getClass(), (String) prop, context);
            if (getter == null) return null;
            try
            {
                return Validator.fromMethod(getter);
            }
            catch (Exception e)
            {
                throw new ExpressException("Could not get validator");
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
    
    @Override
    public boolean isIdempotent()
    {
        return false;
    }
}
