package com.intrbiz.express.template.filter;

import java.io.IOException;
import java.io.Writer;

import com.intrbiz.express.ExpressException;

public class PlainTextContentFilter implements ContentFilter
{
    @Override
    public String getContentType()
    {
        return "text/plain";
    }
    
    @Override
    public void filter(String text, Writer to) throws IOException
    {
        to.write(text);
    }

    @Override
    public String prefilter(String text) throws ExpressException
    {
        return text;
    }
}
