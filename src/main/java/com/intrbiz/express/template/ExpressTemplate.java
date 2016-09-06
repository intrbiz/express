package com.intrbiz.express.template;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.filter.ContentFilter;
import com.intrbiz.express.template.loader.TemplateSource;
import com.intrbiz.express.value.ValueScript;

public class ExpressTemplate
{
    private final String name;

    private final TemplateSource source;

    private final ContentFilter defaultFilter;

    private final ValueScript script;

    public ExpressTemplate(String name, TemplateSource source, ContentFilter defaultFilter, ValueScript script)
    {
        super();
        this.name = name;
        this.source = source;
        this.defaultFilter = defaultFilter;
        this.script = script;
    }

    public String getName()
    {
        return name;
    }

    public TemplateSource getSource()
    {
        return source;
    }

    public ContentFilter getDefaultFilter()
    {
        return defaultFilter;
    }

    public ValueScript getScript()
    {
        return script;
    }
    
    public String toString()
    {
        return "Express Template " + this.name + " source=" + this.source.getClass().getSimpleName() + ", filter=" + this.defaultFilter.getContentType() + "\n" + this.script;
    }

    public void encode(ExpressContext context, Object source) throws ExpressException
    {
        try
        {
            // set the content filter
            context.setContentFilter(this.defaultFilter);
            // process the script
            this.script.execute(context, source);
            // flush
            context.flush();
        }
        finally
        {
            context.resetContentFilter();
        }
    }
}
