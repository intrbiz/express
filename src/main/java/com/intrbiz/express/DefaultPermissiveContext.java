package com.intrbiz.express;

import com.intrbiz.express.security.Limiter;
import com.intrbiz.express.template.filter.ContentFilterRegistry;
import com.intrbiz.express.template.loader.TemplateLoader;

public class DefaultPermissiveContext extends DefaultContext
{
    public DefaultPermissiveContext()
    {
        super();
    }

    public DefaultPermissiveContext(ExpressEntityResolver resolver)
    {
        super(resolver);
    }

    public DefaultPermissiveContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Limiter iterationLimiter, Limiter opLimiter, TemplateLoader templateLoader, ContentFilterRegistry contentFilterRegistry)
    {
        super(extensions, resolver, iterationLimiter, opLimiter, templateLoader, contentFilterRegistry);
    }

    public DefaultPermissiveContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, Limiter iterationLimiter, Limiter opLimiter)
    {
        super(extensions, resolver, iterationLimiter, opLimiter);
    }

    public DefaultPermissiveContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver, TemplateLoader templateLoader)
    {
        super(extensions, resolver, templateLoader);
    }

    public DefaultPermissiveContext(ExpressExtensionRegistry extensions, ExpressEntityResolver resolver)
    {
        super(extensions, resolver);
    }

    public DefaultPermissiveContext(ExpressExtensionRegistry registry)
    {
        super(registry);
    }

    public DefaultPermissiveContext(TemplateLoader templateLoader, ContentFilterRegistry contentFilterRegistry)
    {
        super(templateLoader, contentFilterRegistry);
    }

    public DefaultPermissiveContext(TemplateLoader templateLoader)
    {
        super(templateLoader);
    }

    @Override
    public void checkIteration()
    {
    }

    @Override
    public void checkOp()
    {
    }

    @Override
    public boolean allowSetAccessible()
    {
        return true;
    }

    @Override
    public boolean checkJavaAccess(String className)
    {
        return true;
    }
}
