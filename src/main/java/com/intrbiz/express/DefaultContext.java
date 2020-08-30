package com.intrbiz.express;

import java.io.Writer;

import com.intrbiz.express.action.ActionHandler;
import com.intrbiz.express.operator.Decorator;
import com.intrbiz.express.operator.Function;
import com.intrbiz.express.security.Limiter;
import com.intrbiz.express.stack.ELStatementFrame;
import com.intrbiz.express.template.filter.ContentFilter;
import com.intrbiz.express.template.filter.ContentFilterRegistry;
import com.intrbiz.express.template.io.TemplateWriter;
import com.intrbiz.express.template.loader.TemplateLoader;

public class DefaultContext implements ExpressContext
{
    private final ELStatementFrame root = new ELStatementFrame();

    private ELStatementFrame frame = root;

    private final ExpressExtensionRegistry extensions;

    private final ExpressEntityResolver resolver;
    
    private final Limiter iterationLimiter;
    
    private final Limiter opLimiter;
    
    private boolean caching = true;
    
    private TemplateLoader templateLoader;
    
    private ContentFilterRegistry contentFilterRegistry;
    
    private TemplateWriter writer;

    public DefaultContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Limiter iterationLimiter,  Limiter opLimiter, TemplateLoader templateLoader, ContentFilterRegistry contentFilterRegistry)
    {
        super();
        this.extensions = extensions;
        this.resolver = resolver;
        this.iterationLimiter = iterationLimiter == null ? Limiter.defaultIterationLimit() : iterationLimiter;
        this.opLimiter = opLimiter == null ? Limiter.defaultOpLimit() : opLimiter;
        this.templateLoader = templateLoader;
        this.contentFilterRegistry = contentFilterRegistry;
    }
    
    public DefaultContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver)
    {
        this(extensions, resolver, null, null, null, null);
    }
    
    public DefaultContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Limiter iterationLimiter,  Limiter opLimiter)
    {
        this(extensions, resolver, iterationLimiter, opLimiter, null, null);
    }

    public DefaultContext(ExpressExtensionRegistry registry)
    {
        this(registry, null, null, null, null, null);
    }

    public DefaultContext(ExpressEntityResolver resolver)
    {
        this(ExpressExtensionRegistry.getDefaultRegistry(), resolver, null, null, null, null);
    }

    public DefaultContext()
    {
        this(ExpressExtensionRegistry.getDefaultRegistry(), null, null, null, null, null);
    }
    
    public DefaultContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, TemplateLoader templateLoader)
    {
        this(extensions, resolver, null, null, templateLoader, ContentFilterRegistry.getDefault());
    }
    
    public DefaultContext(TemplateLoader templateLoader, ContentFilterRegistry contentFilterRegistry)
    {
        this(ExpressExtensionRegistry.getDefaultRegistry(), null, null, null, templateLoader, contentFilterRegistry);
    }
    
    public DefaultContext(TemplateLoader templateLoader)
    {
        this(ExpressExtensionRegistry.getDefaultRegistry(), null, null, null, templateLoader, ContentFilterRegistry.getDefault());
    }

    @Override
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

    @Override
    public void setEntity(String name, Object value, Object source)
    {
        if (this.frame != null)
            this.frame.setEntity(name, value);
        else
            this.root.setEntity(name, value);
    }

    @Override
    public void declareEntity(String name, Object value, Object source)
    {
        if (this.frame != null)
            this.frame.declareEntity(name, value);
        else
            this.root.declareEntity(name, value);        
    }

    @Override
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
    
    @Override
    public ExpressExtensionRegistry getExpressExtensionRegistry()
    {
        return this.extensions;
    }

    // Stack Control

    @Override
    public void enterFrame(boolean function)
    {
        this.frame = new ELStatementFrame(this.frame, function);
    }

    @Override
    public void exitFrame()
    {
        if (this.frame != null)
        {
            if (this.frame.getParent() == null) throw new IllegalStateException("You cannot remove the root stack frame!");
            this.frame = this.frame.getParent();
        }
    }

    @Override
    public ELStatementFrame getFrame()
    {
        return this.frame;
    }

    @Override
    public void checkIteration()
    {
        this.iterationLimiter.check();
    }

    @Override
    public void checkOp()
    {
        this.opLimiter.check();
    }

    @Override
    public boolean allowSetAccessible()
    {
        return true;
    }

    @Override
    public boolean suppressMethodExceptions()
    {
        return true;
    }
    
    @Override
    public boolean checkJavaAccess(String className)
    {
        return true;
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
    
    @Override
    public TemplateLoader getTemplateLoader()
    {
        return this.templateLoader;
    }

    @Override
    public ContentFilterRegistry getContentFilterRegistry()
    {
        return this.contentFilterRegistry;
    }

    public void setTemplateLoader(TemplateLoader templateLoader)
    {
        this.templateLoader = templateLoader;
    }

    public void setContentFilterRegistry(ContentFilterRegistry contentFilterRegistry)
    {
        this.contentFilterRegistry = contentFilterRegistry;
    }
    
    @Override
    public TemplateWriter getWriter()
    {
        return this.writer;
    }
    
    @Override
    public void setupWriter(Writer to, ContentFilter defaultFilter)
    {
        this.writer = new TemplateWriter(this, to, defaultFilter);
    }
    
    @Override
    public void clearWriter()
    {
        this.writer = null;
    }
}
