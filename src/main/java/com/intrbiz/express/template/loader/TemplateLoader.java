package com.intrbiz.express.template.loader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.ExpressTemplate;

/**
 * Loading framework for express templates
 */
public class TemplateLoader
{
    private ConcurrentMap<String, ExpressTemplate> cache = new ConcurrentHashMap<String, ExpressTemplate>();
    
    private boolean cacheOn = true;
    
    private CopyOnWriteArrayList<TemplateSource> sources = new CopyOnWriteArrayList<TemplateSource>();
    
    private NameSuffixRegistry nameSuffixRegistry = NameSuffixRegistry.getDefault();
    
    public TemplateLoader()
    {
        super();
    }

    public boolean isCacheOn()
    {
        return cacheOn;
    }

    public void setCacheOn(boolean cacheOn)
    {
        this.cacheOn = cacheOn;
    }
    
    public void devMode()
    {
        this.cacheOn = false;
    }
    
    public List<TemplateSource> getSources()
    {
        return this.sources;
    }
    
    public void addSource(TemplateSource source)
    {
        this.sources.add(source);
    }
    
    public void clearSources()
    {
        this.sources.clear();
    }
    
    public void removeSource(TemplateSource source)
    {
        this.sources.remove(source);
    }
    
    public void clearCache()
    {
        this.cache.clear();
    }
    
    
    
    public NameSuffixRegistry getNameSuffixRegistry()
    {
        return nameSuffixRegistry;
    }

    /**
     * Search through the configured sources and attempt to load the named template
     * @param context the context to parse the template source with
     * @param name the template name
     * @return a template or null
     * @throws ExpressException if a parse error happens
     */
    public ExpressTemplate load(ExpressContext context, String name) throws ExpressException
    {
        // try the cache
        if (this.cacheOn)
        {
            ExpressTemplate template = this.cache.get(name);
            if (template != null) return template;
        }
        // try to load
        ExpressTemplate template = null;
        for (TemplateSource source : this.sources)
        {
            template = source.load(context, this.nameSuffixRegistry, name);
            if (template != null)
                break;
        }
        if (template != null && this.cacheOn)
        {
            this.cache.put(name, template);
        }
        return template;
    }
}
