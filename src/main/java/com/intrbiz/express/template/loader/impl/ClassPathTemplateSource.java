package com.intrbiz.express.template.loader.impl;

import java.io.InputStream;
import java.util.Objects;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.ExpressTemplate;
import com.intrbiz.express.template.loader.NameSuffix;
import com.intrbiz.express.template.loader.NameSuffixRegistry;
import com.intrbiz.express.template.loader.TemplateSource;
import com.intrbiz.express.value.ValueScript;

public class ClassPathTemplateSource implements TemplateSource
{
    private final ClassLoader classLoader;
    
    private final String basePath;
    
    public ClassPathTemplateSource(ClassLoader classLoader, String basePath)
    {
        super();
        Objects.requireNonNull(classLoader, "The class loader cannot be null!");
        this.classLoader = classLoader;
        this.basePath = cleanBasePath(basePath);
    }
    
    public ClassPathTemplateSource(String basePath)
    {
        this(Thread.currentThread().getContextClassLoader(), basePath);
    }
    
    public ClassPathTemplateSource()
    {
        this("/");
    }
    
    private String cleanBasePath(String basePath)
    {
        if (basePath == null || basePath.length() == 0 || "/".equals(basePath)) return "";
        if (basePath.startsWith("/")) basePath = basePath.substring(1);
        if (! basePath.endsWith("/")) basePath = basePath + "/";
        return basePath;
    }
    
    public ClassLoader getClassLoader()
    {
        return this.classLoader;
    }
    
    public String getBasePath()
    {
        return this.basePath;
    }
    
    private String applyBasePathPrefix(String name)
    {
        return name.startsWith("/") ? this.basePath + name.substring(1) : this.basePath + name;
    }

    @Override
    public ExpressTemplate load(ExpressContext context, NameSuffixRegistry nameSuffixRegistry, String name) throws ExpressException
    {
        // search for the template
        for (NameSuffix suffix : nameSuffixRegistry)
        {
            String path = this.applyBasePathPrefix(name) + "." + suffix.getSuffix();
            InputStream resource = this.classLoader.getResourceAsStream(path);
            if (resource != null)
            {
                // parse the template and return it
                return new ExpressTemplate(name, this, suffix.getDefaultContentFilter(), new ValueScript(context, resource));
            }
        }
        return null;
    }
}
