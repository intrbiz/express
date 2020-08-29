package com.intrbiz.express.operator;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.security.Hidden;
import com.intrbiz.express.security.ReadOnly;
import com.intrbiz.express.util.ELReflectUtil;

public class MethodInvoke extends Operator
{
    private Operator left;

    private List<Operator> arguments;

    private Map<String, Operator> namedParameters = new TreeMap<String, Operator>();

    private volatile DecoratorCache decoratorCache = null;
    
    private volatile MethodCache methodCache = null;

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
     * 
     * @return returns Map<String,Operator>
     */
    public Map<String, Operator> getNamedParameters()
    {
        return namedParameters;
    }

    /**
     * Set the functions named parameters
     * 
     * @param namedParameters
     *            returns void
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
    
    protected void checkEntityAccess(Object entity, boolean set) throws ExpressException
    {
        if (entity instanceof Hidden)
            throw new ExpressException("Illegal access to Hidden entity");
        if (set && entity instanceof ReadOnly)
            throw new ExpressException("Illegal access to ReadOnly entity");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        // what are we accessing methods on
        Object on = this.getLeft().get(context, source);
        if (on == null) return null;
        this.checkAccess(on, false);
        // the class of the object we are accessing
        Class<?> cls = on instanceof Class<?> ? (Class<?>) on : on.getClass();
        // evaluate the arguments to assist in overload detection
        Object[] args = new Object[this.getArguments().size()];
        int i = 0;
        for (Operator op : this.getArguments())
        {
            args[i++] = op.get(context, source);
        }
        // find the method to call
        Method method = this.getMethod(cls, args, context);
        if (method != null) 
            return ELReflectUtil.invokeMethod(context, method, on, args);
        // maybe this method is a decorator (virtual method)
        Decorator decorator = this.getDecorator(cls, context);
        if (decorator != null) 
            return decorator.get(context, source);
        return null;
    }
    
    protected void checkAccess(Object entity, boolean set) throws ExpressException
    {
        if (entity instanceof Hidden)
            throw new ExpressException("Ilegal access to Hidden entity");
        if (set && entity instanceof ReadOnly)
            throw new ExpressException("Ilegal access to ReadOnly entity");
    }
    
    protected Decorator getDecorator(Class<?> onClass, ExpressContext context)
    {
        DecoratorCache cache = this.decoratorCache;
        if (cache == null || onClass != cache.type)
        {
            // get the decorator for this type
            Decorator d = context.getCustomDecorator(this.getName(), onClass);
            if (d != null)
            {
                d.setEntity(this.getLeft());
                d.setParameters(this.getArguments());
                d.setNamedParameters(this.getNamedParameters());
                // cache the loaded decorator
                cache = new DecoratorCache(d, onClass);
                if (context.isCaching()) this.decoratorCache = cache;
            }
        }
        return cache == null ? null : cache.decorator;
    }
    
    protected Method getMethod(Class<?> onClass, Object[] args, ExpressContext context)
    {
        MethodCache cache = this.methodCache;
        if (cache == null || onClass != cache.type)
        {
            cache = null;
            Set<Method> nameMatched = new HashSet<>();
            for (Method method : onClass.getMethods())
            {
                if (method.getName().equals(this.getName()))
                {
                    if (ELReflectUtil.methodMatch(method, args))
                    {
                        if (context.allowSetAccessible()) method.setAccessible(true);
                        cache = new MethodCache(method, onClass);
                        if (context.isCaching()) this.methodCache = cache;
                        break;
                    }
                    else
                    {
                        nameMatched.add(method);
                    }
                }
            }
            if (cache == null && nameMatched.size() == 1)
            {
                Method method = nameMatched.iterator().next();
                if (context.allowSetAccessible()) method.setAccessible(true);
                cache = new MethodCache(method, onClass);
                if (context.isCaching()) this.methodCache = cache;
            }
        }
        return cache == null ? null : cache.method;
    }

    private static class DecoratorCache
    {
        public final Decorator decorator;

        public final Class<?> type;

        public DecoratorCache(Decorator decorator, Class<?> type)
        {
            this.decorator = decorator;
            this.type = type;
        }
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
    public boolean isConstant()
    {
        return false;
    }
}
