package com.intrbiz.express.script;

import java.io.StringReader;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import com.intrbiz.express.DefaultScriptContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.ExpressExtensionRegistry;
import com.intrbiz.express.parser.ELParser;
import com.intrbiz.express.parser.ParseException;
import com.intrbiz.express.parser.TokenMgrError;
import com.intrbiz.express.script.cache.ScriptCache;
import com.intrbiz.express.script.cache.WeakScriptCache;
import com.intrbiz.express.value.ValueScript;

public class ExpressScriptEngineFactory
{
    protected final ExpressExtensionRegistry globalRegistry = new ExpressExtensionRegistry("global");
    
    protected final Map<String, Boolean> allowedJavaClasses = new TreeMap<>();
    
    protected final ScriptCache cache;
    
    public ExpressScriptEngineFactory(ScriptCache cache)
    {
        super();
        this.cache = Objects.requireNonNull(cache);
    }
    
    public ExpressScriptEngineFactory(ScriptCache cache, ExpressExtensionRegistry... subRegistries)
    {
        this(cache);
        for (ExpressExtensionRegistry registry : subRegistries)
        {
            this.globalRegistry.addSubRegistry(registry);
        }
    }
    
    public ExpressScriptEngineFactory()
    {
        this(new WeakScriptCache());
    }
    
    public ExpressScriptEngineFactory(ExpressExtensionRegistry... subRegistries)
    {
        this(new WeakScriptCache(), subRegistries);
    }
    
    public ExpressExtensionRegistry getGlobalRegistry()
    {
        return this.globalRegistry;
    }
    
    public ExpressScriptEngineFactory addAllowedJavaClass(String javaClassPattern, boolean allowed)
    {
        this.allowedJavaClasses.put(javaClassPattern, allowed);
        return this;
    }
    
    public boolean checkJavaAccess(String className)
    {
        Boolean result = this.allowedJavaClasses.get(className);
        return result == null ? false : result;
    }

    public final String wrap(String plainScript) {
        return "<# " + plainScript + " #>";
    }
    
    public void verify(String script) throws ExpressException
    {
        ExpressExtensionRegistry registry = new ExpressExtensionRegistry("script", this.globalRegistry);
        try
        {
            new ELParser(new StringReader(script)).verify().readFullStatements(new DefaultScriptContext(registry));
        }
        catch (TokenMgrError e)
        {
            throw new ExpressException("Error parsing script (" + e.getMessage() + "): [" + script + "]", e);
        }
        catch (ExpressException ee)
        {
            throw new ExpressException("Error parsing script (" + ee.getMessage() + "): [" + script + "]", ee);
        }
        catch (ParseException pe)
        {
            throw new ExpressException("Error parsing script (" + pe.getMessage() + "): [" + script + "]", pe);
        }
    }
    
    public final void verifyUnwrapped(String script) throws ExpressException
    {
        this.verify(this.wrap(script));
    }
    
    public ExpressScriptEngine parse(String script) throws ExpressException
    {
        ExpressExtensionRegistry registry = new ExpressExtensionRegistry("script", this.globalRegistry);
        return new ExpressScriptEngine(registry, this::checkJavaAccess, new ValueScript(new DefaultScriptContext(registry), script));
    }
    
    public final ExpressScriptEngine parseUnwrapped(String script) throws ExpressException
    {
        return this.parse(this.wrap(script));
    }
}
