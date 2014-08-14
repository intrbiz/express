package com.intrbiz.express.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

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
    
    public static Object invokeMethod(Method method, Object on, Object... args)
    {
        try
        {
            return method.invoke(on, args);
        }
        catch (Exception e)
        {
            logger.warn("Suppressing exception from " + method, e);
        }
        return null;
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
                else if (arg != null && (!type.isInstance(arg))) 
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
