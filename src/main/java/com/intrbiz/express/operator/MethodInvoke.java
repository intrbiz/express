package com.intrbiz.express.operator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class MethodInvoke extends Operator
{

    private Operator left;

    private List<Operator> arguments;
    
    private Map<String, Operator> namedParameters = new TreeMap<String, Operator>();
    
    private static final AtomicReferenceFieldUpdater<MethodInvoke, DecoratorCache> cacheUpdater = AtomicReferenceFieldUpdater.newUpdater(MethodInvoke.class, DecoratorCache.class, "cache");

    private volatile DecoratorCache cache = null;

    public MethodInvoke(Operator e, String methodName, List<Operator> arguments, Map<String, Operator> namedParameters)
    {
        super(methodName);
        this.setLeft(e);
        this.setArguments(arguments);
        this.setNamedParameters(namedParameters);
    }

    public List<Operator> getArguments()
    {
        return arguments;
    }

    public void setArguments(List<Operator> arguments)
    {
        this.arguments = arguments;
    }
    
    /**
     * Get the functions named parameters
     * @return
     * returns Map<String,Operator>
     */
    public Map<String, Operator> getNamedParameters()
    {
        return namedParameters;
    }

    /**
     * Set the functions named parameters
     * @param namedParameters
     * returns void
     */
    public void setNamedParameters(Map<String, Operator> namedParameters)
    {
        this.namedParameters = namedParameters;
    }

    public Operator getLeft()
    {
        return left;
    }

    public void setLeft(Operator left)
    {
        this.left = left;
    }

    public String toString()
    {
        StringBuilder ret = new StringBuilder(this.getLeft().toString());
        ret.append(".").append(this.getName()).append("(");
        boolean npa = false;
        for (Operator op : this.getArguments())
        {
            if (npa) ret.append(", ");
            ret.append(op.toString());
            npa = true;
        }
        for (Entry<String, Operator> nop : this.getNamedParameters().entrySet())
        {
            if (npa) ret.append(", ");
            ret.append(nop.getKey()).append(" = ").append(nop.getValue());
            npa = true;
        }
        ret.append(")");
        return ret.toString();
    }

    @Override
    public Object get(ELContext context, Object source) throws ELException
    {
        Object on  = this.getLeft().get(context, source);
        if (on != null)
        {
            // evaluate the arguments to assist in overload detection
            Object[] args = new Object[this.getArguments().size()];
            int i = 0;
            for (Operator op : this.getArguments())
            {
                args[i++] = op.get(context, source);
            }
            // get the class for find the method to call
            Class<?> cls = on.getClass();
            for (Method m : cls.getMethods())
            {
                if ( m.getName().equals(this.getName()) && this.methodMatch(args, m))
                {
                    try
                    {
                        return m.invoke(on, args);
                    }
                    catch (IllegalArgumentException e)
                    {
                    }
                    catch (IllegalAccessException e)
                    {
                    }
                    catch (InvocationTargetException e)
                    {
                        Throwable te = e.getTargetException();
                        if (te instanceof RuntimeException)
                        {
                            throw (RuntimeException) te;
                        }
                        else if (te instanceof Error)
                        {
                            throw (Error) te;
                        }
                        else if (te instanceof ELException)
                        {
                            throw (ELException) te;
                        }
                    }
                }
            }
            // a decorator
            DecoratorCache cache = this.cache;
            if ( ! (cache != null && cls.equals(cache.type)))
            {
                // get the decorator for this type
                Decorator d = context.getCustomDecorator(this.getName(), cls);
                if (d != null)
                {
                    d.setEntity(this.getLeft());
                    d.setParameters(this.getArguments());
                    d.setNamedParameters(this.getNamedParameters());
                    cache = new DecoratorCache(d, cls);
                    // cache
                    cacheUpdater.set(this, cache);
                }
                else
                {
                    // TODO this.logger.warn("Failed to load decorator: " + this.getName() + " for type: " + cls.getName());
                }
            }
            if (cache != null)
            {
                return cache.decorator.get(context, source);
            }
        }
        return null;
    }
    
    protected boolean methodMatch(Object[] args, Method m)
    {
        Class<?>[] types = m.getParameterTypes();
        if (args.length == types.length)
        {
            for (int i = 0; i < args.length; i++)
            {
                Object arg = args[i];
                Class<?> type = types[i];
                if (arg != null && (!type.isInstance(arg))) return false;
            }
        }
        return true;
    }
    
    private static class DecoratorCache
    {
        public Decorator decorator;

        public Class<?> type;

        public DecoratorCache(Decorator decorator, Class<?> type)
        {
            this.decorator = decorator;
            this.type = type;
        }
    }
}
