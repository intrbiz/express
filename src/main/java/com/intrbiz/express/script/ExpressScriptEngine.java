package com.intrbiz.express.script;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import com.intrbiz.express.DefaultScriptContext;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressEntityResolver;
import com.intrbiz.express.ExpressExtensionRegistry;
import com.intrbiz.express.value.ScriptReturn;
import com.intrbiz.express.value.ValueScript;

public final class ExpressScriptEngine
{
    private final Function<String, Boolean> globalAllowedJavaClasses;
    
    private final Map<String, Boolean> allowedJavaClasses = new TreeMap<>();
    
    private final ExpressExtensionRegistry registry;
    
    private final ValueScript script;
    
    public ExpressScriptEngine(ExpressExtensionRegistry registry, Function<String, Boolean> globalAllowedJavaClasses, ValueScript script)
    {
        super();
        this.registry = registry;
        this.globalAllowedJavaClasses = globalAllowedJavaClasses;
        this.script = script;
    }
    
    public ExpressScriptEngine addAllowedJavaClass(String javaClassPattern, boolean allowed)
    {
        this.allowedJavaClasses.put(javaClassPattern, allowed);
        return this;
    }
    
    public boolean checkJavaAccess(String className)
    {
        Boolean result = this.allowedJavaClasses.get(className);
        return result == null ? this.globalAllowedJavaClasses.apply(className) : result;
    }
    
    public ExpressExtensionRegistry getExpressExtensionRegistry()
    {
        return this.registry;
    }
    
    public ExpressContext createContext(ExpressEntityResolver bindings)
    {
        return new DefaultScriptContext(this.registry, bindings, this::checkJavaAccess);
    }
    
    public ExpressContext createContext()
    {
        return this.createContext(null);
    }
    
    public Object execute(ExpressContext context, Object source)
    {
        return this.script.execute(context, source);
    }
    
    public ScriptReturn executeWithState(ExpressContext context, Object source)
    {
        return this.script.executeWithState(context, source);
    }
}
