package com.intrbiz.express.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class ELReflectUtil
{
    private static Logger logger = Logger.getLogger(ELReflectUtil.class);
    
    public static Method findGetter(Class<?> entityClass, String field)
    {
        String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
        Method[] methods = entityClass.getMethods();
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName))
                return method;
        }
        methodName = "is" + field.substring(0, 1).toUpperCase() + field.substring(1);
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName))
                return method;
        }
        methodName = field;
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
    }

    public static Method findSetter(Class<?> onClass, String field)
    {
        String methodName = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
        Method[] methods = onClass.getMethods();
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName)) { return method; }
        }
        methodName = field;
        for (int i = 0; i < methods.length; i++)
        {
            Method method = methods[i];
            if (method.getName().equals(methodName)) { return method; }
        }
        return null;
    }
    
    public static Object invokeMethod(ExpressContext context, Method method, Object on, Object... args)
    {
        try
        {
            // map the argument types
            Object[] mapped = args;
            Class<?>[] types = method.getParameterTypes();
            // map the inputs
            if (types.length > 0)
            {
                mapped = new Object[types.length];
                if (types[types.length - 1].isArray())
                {
                    // varargs support
                    for (int i = 0; i < (types.length - 1) && i < args.length; i++)
                    {
                        mapped[i] = mapArgument(types[i], args[i]);
                    }
                    if (args.length >= types.length)
                    {
                        int offset = types.length - 1;
                        int len = args.length - offset;
                        Class<?> arrayType = types[offset];
                        Class<?> elementType = arrayType.getComponentType();
                        Object varargs = (Object[]) Array.newInstance(elementType, len);
                        for (int i = offset; i < args.length && i < len; i++) {
                            Array.set(varargs, i - offset, mapArgument(elementType, args[i]));
                        }
                        mapped[offset] = varargs;
                    }
                }
                else
                {
                    for (int i = 0; i < types.length && i < args.length; i++)
                    {
                        mapped[i] = mapArgument(types[i], args[i]);
                    }
                }
            }
            // invoke
            return method.invoke(on, mapped);
        }
        catch (InvocationTargetException e)
        {
            if (context.suppressMethodExceptions())
                logger.warn("Suppressing exception from " + method, e);
            else
                throw new ExpressException(e.getCause());
        }
        catch (Exception e)
        {
            if (context.suppressMethodExceptions())
                logger.warn("Suppressing exception from " + method, e);
            else
                throw new ExpressException(e);
        }
        return null;
    }
    
    public static Object mapArgument(Class<?> type, Object arg)
    {
        if (arg instanceof InvocationHandler)
        {
            return Proxy.newProxyInstance(arg.getClass().getClassLoader(), new Class<?>[] { type }, (InvocationHandler) arg);
        }
        return arg;
    }
    
    public static boolean methodMatch(Method m, Object[] args)
    {
        Class<?>[] types = m.getParameterTypes();
        if (args.length == types.length)
        {
            for (int i = 0; i < args.length; i++)
            {
                Object arg = args[i];
                Class<?> type = types[i];
                //
                if (isPrimitive(type))
                {
                    if (! primitiveInstanceOf(type, arg))
                    {
                        return false;
                    }
                }
                else if (arg != null && (! matchType(type, arg))) 
                {
                    return false;
                }
            }
        }
        else if (types.length > 0 && args.length > types.length && types[types.length - 1].isArray())
        {
            int offset = types.length - 1;
            // possible var args
            for (int i = 0; i < offset; i++)
            {
                Object arg = args[i];
                Class<?> type = types[i];
                //
                if (isPrimitive(type))
                {
                    if (! primitiveInstanceOf(type, arg))
                    {
                        return false;
                    }
                }
                else if (arg != null && (! matchType(type, arg))) 
                {
                    return false;
                }
            }
            Class<?> type = types[offset].getComponentType();
            for (int i = offset; i < args.length; i++)
            {
                Object arg = args[i];
                if (isPrimitive(type))
                {
                    if (! primitiveInstanceOf(type, arg))
                    {
                        return false;
                    }
                }
                else if (arg != null && (! matchType(type, arg))) 
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
        return true;
    }
    
    public static boolean matchType(Class<?> type, Object obj)
    {
        if (type.isInstance(obj))
            return true;
        if (obj instanceof InvocationHandler)
            return true;
        return false;
    }
    
    public static boolean isPrimitive(Class<?> parameterType)
    {
        return int.class == parameterType 
            || long.class == parameterType 
            || float.class == parameterType 
            || double.class == parameterType 
            || boolean.class == parameterType 
            || short.class == parameterType 
            || byte.class == parameterType 
            || char.class == parameterType;
    }

    public static boolean primitiveInstanceOf(Class<?> parameterType, Object obj)
    {
        if (int.class == parameterType)
        {
            return obj instanceof Integer;
        }
        else if (long.class == parameterType)
        {
            return obj instanceof Long;
        }
        else if (float.class == parameterType)
        {
            return obj instanceof Float;
        }
        else if (double.class == parameterType)
        {
            return obj instanceof Double;
        }
        else if (boolean.class == parameterType)
        {
            return obj instanceof Boolean;
        }
        else if (short.class == parameterType)
        {
            return obj instanceof Short;
        }
        else if (byte.class  == parameterType)
        {
            return obj instanceof Byte;
        }
        else if (char.class == parameterType)
        {
            return obj instanceof Character;
        }
        return false;
    }
}
