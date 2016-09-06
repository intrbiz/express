package com.intrbiz.express.template.loader;

import com.intrbiz.express.template.filter.ContentFilter;

public class NameSuffix
{
    private final String suffix;
    
    private final ContentFilter defaultContentFilter;

    public NameSuffix(String suffix, ContentFilter defaultContentFilter)
    {
        super();
        this.suffix = suffix;
        this.defaultContentFilter = defaultContentFilter;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public ContentFilter getDefaultContentFilter()
    {
        return defaultContentFilter;
    }
}