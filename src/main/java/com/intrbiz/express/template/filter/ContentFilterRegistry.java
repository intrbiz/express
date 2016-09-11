package com.intrbiz.express.template.filter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ContentFilterRegistry
{
    private ConcurrentMap<String, ContentFilter> filters = new ConcurrentHashMap<String, ContentFilter>();
    
    public ContentFilterRegistry()
    {
        super();
    }
    
    public void registerFilter(ContentFilter theFilter)
    {
        if (theFilter != null)
            this.filters.putIfAbsent(theFilter.getContentType().toLowerCase(), theFilter);
    }
    
    public ContentFilter getFilter(String contentType)
    {
        if (contentType == null)
            return null;
        return this.filters.get(contentType.toLowerCase());
    }
    
    public static ContentFilterRegistry getDefault()
    {
        ContentFilterRegistry reg = new ContentFilterRegistry();
        reg.registerFilter(new PlainTextContentFilter());
        reg.registerFilter(new HTMLContentFilter());
        reg.registerFilter(new XMLContentFilter());
        return reg;
    }
}
