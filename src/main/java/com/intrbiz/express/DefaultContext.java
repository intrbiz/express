package com.intrbiz.express;

import com.intrbiz.express.action.ActionHandler;
import com.intrbiz.express.operator.Decorator;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.stack.ELStatementFrame;

public class DefaultContext implements ExpressContext
{
    private final ELStatementFrame root = new ELStatementFrame();

    private ELStatementFrame frame = root;

    private final ExpressExtensionRegistry extensions;

    private final ExpressEntityResolver resolver;
    
    private boolean caching = true;

    public DefaultContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver)
    {
        super();
        this.extensions = extensions;
        this.resolver = resolver;
    }

    public DefaultContext(ExpressExtensionRegistry registry)
    {
        this(registry, null);
    }

    public DefaultContext(ExpressEntityResolver resolver)
    {
        this(ExpressExtensionRegistry.getDefaultRegistry(), resolver);
    }

    public DefaultContext()
    {
        this(ExpressExtensionRegistry.getDefaultRegistry(), null);
    }

    public Object getEntity(String name, Object source)
    {
        if (this.frame != null && this.frame.containsEntity(name))
        {
            return this.frame.getEntity(name);
        }
        else if (this.root.containsEntity(name)) { return this.root.getEntity(name); }
        return this.getEntityInner(name, source);
    }

    protected Object getEntityInner(String name, Object source)
    {
        if (this.resolver == null) return null;
        return this.resolver.getEntity(name, source);
    }

    @Override
    public ActionHandler getAction(String name, Object source)
    {
        if (this.resolver == null) return null;
        return this.resolver.getAction(name, source);
    }

    public void setEntity(String name, Object value, Object source)
    {
        if (this.frame != null)
            this.frame.setEntity(name, value);
        else
            this.root.setEntity(name, value);
    }

    public void exportEntity(String name, Object value, Object source)
    {
        // Export the entity to the root of the stack
        this.root.setEntity(name, value);
    }

    // default implementations to make like simpler

    @Override
    public Function getCustomFunction(String name)
    {
        if (this.extensions == null) return null;
        return this.extensions.loadFunction(name);
    }

    @Override
    public Decorator getCustomDecorator(String name, Class<?> entityType)
    {
        if (this.extensions == null) return null;
        return this.extensions.loadDecorator(name, entityType);
    }

    // Stack Control

    public void enterFrame(boolean function)
    {
        this.frame = new ELStatementFrame(this.frame, function);
    }

    public void exitFrame()
    {
        if (this.frame != null)
        {
            if (this.frame.getParent() == null) throw new IllegalStateException("You cannot remove the root stack frame!");
            this.frame = this.frame.getParent();
        }
    }

    public ELStatementFrame getFrame()
    {
        return this.frame;
    }

    @Override
    public boolean isCaching()
    {
        return this.caching;
    }

    @Override
    public void setCaching(boolean caching)
    {
        this.caching = caching;
    }
}
