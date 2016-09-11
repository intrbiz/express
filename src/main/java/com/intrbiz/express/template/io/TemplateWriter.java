package com.intrbiz.express.template.io;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.filter.ContentFilter;

public class TemplateWriter
{
    private final ExpressContext context;
    
    private final Writer to;
    
    private final Stack<ContentFilter> filterStack = new Stack<ContentFilter>();
    
    
    public TemplateWriter(ExpressContext context, Writer to, ContentFilter defaultFilter)
    {
        this.context = context;
        this.to = to;
        this.filterStack.push(defaultFilter);
    }
    
    public ExpressContext getContext()
    {
        return this.context;
    }
    
    public void write(String content) throws ExpressException
    {
        try
        {
            this.getContentFilter().filter(content, this.to);
        }
        catch (IOException e)
        {
            throw new ExpressException("Write failed", e);
        }
    }
    
    public void writePrefiltered(String content) throws ExpressException
    {
        try
        {
            this.to.write(content);
        }
        catch (IOException e)
        {
            throw new ExpressException("Write failed", e);
        }
    }
    
    public void pushContentFilter(ContentFilter filter)
    {
        this.filterStack.push(filter);
    }
    
    public void pushContentFilter(String contentType) throws ExpressException
    {
        // load the filter
        ContentFilter filter = this.getContext().getContentFilterRegistry().getFilter(contentType);
        if (filter == null) throw new ExpressException("Could not find content filter for content type: " + contentType);
        this.pushContentFilter(filter);
    }
    
    public void popContentFilter()
    {
        this.filterStack.pop();
    }
    
    public ContentFilter getContentFilter()
    {
        return this.filterStack.peek();
    }
    
    public void flush() throws ExpressException
    {
        try
        {
            this.to.flush();
        }
        catch (IOException e)
        {
            throw new ExpressException("Failed to flush stream", e);
        }
    }
}
