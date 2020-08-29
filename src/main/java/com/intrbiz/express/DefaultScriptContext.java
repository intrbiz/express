package com.intrbiz.express;

import java.util.function.Function;

import com.intrbiz.express.security.Limiter;
import com.intrbiz.express.template.filter.ContentFilterRegistry;
import com.intrbiz.express.template.loader.TemplateLoader;

public class DefaultScriptContext extends DefaultContext
{
    private final Function<String, Boolean> allowedJavaClassPrefixes;
    
    public DefaultScriptContext(Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super();
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }

    public DefaultScriptContext(ExpressEntityResolver resolver, Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super(resolver);
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }

    public DefaultScriptContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Limiter iterationLimiter, Limiter opLimiter, TemplateLoader templateLoader, ContentFilterRegistry contentFilterRegistry, Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super(extensions, resolver, iterationLimiter, opLimiter, templateLoader, contentFilterRegistry);
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }

    public DefaultScriptContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Limiter iterationLimiter, Limiter opLimiter, Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super(extensions, resolver, iterationLimiter, opLimiter);
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }

    public DefaultScriptContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, TemplateLoader templateLoader, Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super(extensions, resolver, templateLoader);
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }

    public DefaultScriptContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super(extensions, resolver);
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }

    public DefaultScriptContext(ExpressExtensionRegistry registry, Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super(registry);
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }

    public DefaultScriptContext(TemplateLoader templateLoader, ContentFilterRegistry contentFilterRegistry, Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super(templateLoader, contentFilterRegistry);
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }

    public DefaultScriptContext(TemplateLoader templateLoader, Function<String, Boolean> allowedJavaClassPrefixes)
    {
        super(templateLoader);
        this.allowedJavaClassPrefixes = allowedJavaClassPrefixes;
    }
    
    public DefaultScriptContext()
    {
        super();
        this.allowedJavaClassPrefixes = null;
    }

    public DefaultScriptContext(ExpressEntityResolver resolver)
    {
        super(resolver);
        this.allowedJavaClassPrefixes = null;
    }

    public DefaultScriptContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Limiter iterationLimiter, Limiter opLimiter, TemplateLoader templateLoader, ContentFilterRegistry contentFilterRegistry)
    {
        super(extensions, resolver, iterationLimiter, opLimiter, templateLoader, contentFilterRegistry);
        this.allowedJavaClassPrefixes = null;
    }

    public DefaultScriptContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Limiter iterationLimiter, Limiter opLimiter)
    {
        super(extensions, resolver, iterationLimiter, opLimiter);
        this.allowedJavaClassPrefixes = null;
    }

    public DefaultScriptContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, TemplateLoader templateLoader)
    {
        super(extensions, resolver, templateLoader);
        this.allowedJavaClassPrefixes = null;
    }

    public DefaultScriptContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver)
    {
        super(extensions, resolver);
        this.allowedJavaClassPrefixes = null;
    }

    public DefaultScriptContext(ExpressExtensionRegistry registry)
    {
        super(registry);
        this.allowedJavaClassPrefixes = null;
    }

    public DefaultScriptContext(TemplateLoader templateLoader, ContentFilterRegistry contentFilterRegistry)
    {
        super(templateLoader, contentFilterRegistry);
        this.allowedJavaClassPrefixes = null;
    }

    public DefaultScriptContext(TemplateLoader templateLoader)
    {
        super(templateLoader);
        this.allowedJavaClassPrefixes = null;
    }

    @Override
    public boolean suppressMethodExceptions()
    {
        return false;
    }

    @Override
    public boolean checkJavaAccess(String className)
    {
        if (className != null)
        {
            if (this.allowedJavaClassPrefixes.apply(className))
                return true;
            // check package wildcards
            int index = className.lastIndexOf('.');
            while (index > 0)
            {
                if (this.allowedJavaClassPrefixes.apply(className.substring(0, index) + ".*"))
                    return true;
                index = className.lastIndexOf('.', index - 1);
            }
            // check global wildcard
            if (this.allowedJavaClassPrefixes.apply("*"))
                return true;
        }
        return false;
    }
}
