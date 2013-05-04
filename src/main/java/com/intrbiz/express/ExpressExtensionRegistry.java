package com.intrbiz.express;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.intrbiz.express.operator.Decorator;
import com.intrbiz.express.operator.Function;

/**
 * A registry of Express extensions
 */
public class ExpressExtensionRegistry
{
    private static final ExpressExtensionRegistry DEFAULT = new ExpressExtensionRegistry("default");
    
    public static final ExpressExtensionRegistry getDefaultRegistry()
    {
        return DEFAULT;
    }
    
    protected final String name;
    
    protected final ConcurrentMap<String, FunctionFactory> functions = new ConcurrentHashMap<String, FunctionFactory>();
    
    protected final ConcurrentMap<DecoratorName, DecoratorFactory> decorators = new ConcurrentHashMap<DecoratorName, DecoratorFactory>();
    
    protected final ConcurrentMap<String, ExpressExtensionRegistry> subRegistries = new ConcurrentHashMap<String, ExpressExtensionRegistry>();
    
    public ExpressExtensionRegistry(String name)
    {
        super();
        if (name == null) throw new NullPointerException("Name cannot be null");
        this.name = name;
    }
    
    public final String getName()
    {
        return this.name;
    }
    
    //
    
    public Collection<ExpressExtensionRegistry> getSubRegistries()
    {
        return Collections.unmodifiableCollection(this.subRegistries.values());
    }
    
    public ExpressExtensionRegistry addSubRegistry(ExpressExtensionRegistry registry)
    {
        this.subRegistries.put(registry.getName(), registry);
        return this;
    }
    
    //
    
    public ExpressExtensionRegistry addFunction(String name, final Class<? extends Function> functionClass)
    {
        return this.addFunction(name, new FunctionFactory() {
            @Override
            public Function loadFunction()
            {
                try
                {
                    return functionClass.newInstance();
                }
                catch (InstantiationException | IllegalAccessException e)
                {
                }
                return null;
            }
        });
    }
    
    public ExpressExtensionRegistry addFunction(final Function immutableFunction)
    {
        return this.addFunction(immutableFunction.getName(), new FunctionFactory() {
            @Override
            public Function loadFunction()
            {
                return immutableFunction;
            }
        });
    }
    
    public ExpressExtensionRegistry addFunction(String name, FunctionFactory factory)
    {
        this.functions.put(name.toLowerCase(), factory);
        return this;
    }
    
    public boolean containsFunction(String name)
    {
        String lName = name.toLowerCase();
        //
        if (this.functions.containsKey(lName)) return true;
        for (ExpressExtensionRegistry reg : this.subRegistries.values())
        {
            if (reg.containsFunction(lName)) return true;
        }
        return false;
    }
    
    public Function loadFunction(String name)
    {
        String lName = name.toLowerCase();
        //
        FunctionFactory ff = this.functions.get(lName);
        if (ff != null) return ff.loadFunction();
        // try the subs
        for (ExpressExtensionRegistry sub : this.subRegistries.values())
        {
            Function f = sub.loadFunction(lName);
            if (f != null) return f;
        }
        return null;
    }
    
    //
    
    public ExpressExtensionRegistry addDecorator(String name, Class<?> entityType, final Class<? extends Decorator> decoratorClass)
    {
        return this.addDecorator(name, entityType, new DecoratorFactory(){
            @Override
            public Decorator loadDecorator()
            {
                try
                {
                    return decoratorClass.newInstance();
                }
                catch (InstantiationException | IllegalAccessException e)
                {
                }
                return null;
            }            
        });
    }
    
    public ExpressExtensionRegistry addDecorator(String name, Class<?> entityType, DecoratorFactory factory)
    {
        this.decorators.put(new DecoratorName(name, entityType), factory);
        return this;
    }
    
    public boolean containsDecorator(String name, Class<?> entityType)
    {
        if (this.decorators.containsKey(new DecoratorName(name, entityType))) return true;
        for (ExpressExtensionRegistry sub : this.subRegistries.values())
        {
            if (sub.containsDecorator(name, entityType)) return true;
        }
        return false;
    }
    
    public Decorator loadDecorator(String name, Class<?> entityType)
    {
        DecoratorFactory df = this.decorators.get(new DecoratorName(name, entityType));
        if (df != null) return df.loadDecorator();
        // subs
        for (ExpressExtensionRegistry sub : this.subRegistries.values())
        {
            Decorator d = sub.loadDecorator(name, entityType);
            if (d != null) return d;
        }
        return null;
    }
    
    //
    
    public static interface FunctionFactory
    {
        Function loadFunction();
    }
    
    public static interface DecoratorFactory
    {
        Decorator loadDecorator();
    }
    
    public static final class DecoratorName
    {
        public final String name;
        public final Class<?> entityType;
        
        public DecoratorName(String name, Class<?> entityType)
        {
            super();
            this.name = name.toLowerCase();
            this.entityType = entityType;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((entityType == null) ? 0 : entityType.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            DecoratorName other = (DecoratorName) obj;
            if (entityType == null)
            {
                if (other.entityType != null) return false;
            }
            else if (entityType != other.entityType) return false;
            if (name == null)
            {
                if (other.name != null) return false;
            }
            else if (!name.equals(other.name)) return false;
            return true;
        }
        
        public String toString()
        {
            return this.entityType + ":" + this.name;
        }
    }
    
    public String toString(String indent)
    {
        StringBuilder sb = new StringBuilder();
        //
        sb.append(indent).append("ExpressExtensionRegistry ").append(this.getName()).append(" {\r\n");
        sb.append(indent).append("  Functions: [\r\n");
        for (String name : this.functions.keySet())
        {
            sb.append(indent).append("    ").append(name).append(",\r\n");
        }
        sb.append(indent).append("  ]\r\n");
        sb.append(indent).append("  Decorators: [\r\n");
        for (DecoratorName name : this.decorators.keySet())
        {
            sb.append(indent).append("    ").append(name).append(",\r\n");
        }
        sb.append(indent).append("  ]\r\n");
        sb.append(indent).append("  SubRegistries: [\r\n");
        for (ExpressExtensionRegistry sub : this.subRegistries.values())
        {
            sb.append( sub.toString(indent + "    ") ).append(",\r\n");
        }
        sb.append(indent).append("  ]\r\n");
        sb.append(indent).append("}");
        //
        return sb.toString();
    }
    
    public String toString()
    {
        return this.toString("");
    }
}
