package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.io.TemplateWriter;

/**
 * Write text to the context
 */
public class PlainWriteStatement extends Statement
{
    private String text;

    public PlainWriteStatement()
    {
        super();
    }

    public PlainWriteStatement(String text)
    {
        this();
        this.text = text;
    }

    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        TemplateWriter writer = ctx.getWriter();
        if (writer != null) writer.writePrefiltered(this.text);
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String toString(String p)
    {
        return this.text;
    }
}
