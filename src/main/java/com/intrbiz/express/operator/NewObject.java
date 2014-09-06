package com.intrbiz.express.operator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class NewObject extends Operator
{
    private String className;

    private List<Operator> arguments;

    public NewObject()
    {
        super("new");
    }

    public NewObject(String name, List<Operator> arguments)
    {
        this();
        this.setClassName(name);
        this.setArguments(arguments);
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public List<Operator> getArguments()
    {
        return arguments;
    }

    public void setArguments(List<Operator> arguments)
    {
        this.arguments = arguments;
    }

    protected boolean constructorMatch(Object[] args, Constructor<?> c)
    {
        Class<?>[] types = c.getParameterTypes();
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

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        try
        {
            Class<?> cls = Class.forName(this.getClassName());
            if (this.getArguments().isEmpty())
            {
                return cls.newInstance();
            }
            else
            {
                Object[] args = new Object[this.getArguments().size()];
                int i = 0;
                for (Operator op : this.getArguments())
                {
                    args[i++] = op.get(context, source);
                }
                // call the correct constructor
                Constructor<?>[] cs = cls.getConstructors();
                for (Constructor<?> c : cs)
                {
                    if (constructorMatch(args, c))
                    {
                        return c.newInstance( args );
                    }
                }
                throw new ExpressException("Could not create new object: " + this.getClassName());
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new ExpressException("Could not create new object: " + this.getClassName(), e);
        }
        catch (InstantiationException e)
        {
            throw new ExpressException("Could not create new object: " + this.getClassName(), e);
        }
        catch (IllegalAccessException e)
        {
            throw new ExpressException("Could not create new object: " + this.getClassName(), e);
        }
        catch (IllegalArgumentException e)
        {
            throw new ExpressException("Could not create new object: " + this.getClassName(), e);
        }
        catch (InvocationTargetException e)
        {
            throw new ExpressException("Could not create new object: " + this.getClassName(), e);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getName());
        sb.append(" ").append(this.getClassName()).append("(");
        boolean na = false;
        for (Operator op : this.getArguments())
        {
            if (na) sb.append(", ");
            sb.append(op.toString());
            na = true;
        }
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public boolean isConstant()
    {
        return false;
    }
}
