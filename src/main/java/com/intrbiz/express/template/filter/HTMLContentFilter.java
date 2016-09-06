package com.intrbiz.express.template.filter;

import java.io.IOException;
import java.io.Writer;

import com.intrbiz.Util;
import com.intrbiz.express.ExpressException;

public class HTMLContentFilter implements ContentFilter
{
    @Override
    public String getContentType()
    {
        return "text/html";
    }
    
    @Override
    public void filter(String text, Writer to) throws IOException
    {
        Util.htmlEncode(text, to);
    }

    @Override
    public String prefilter(String text) throws ExpressException
    {
        return Util.htmlEncode(text);
    }
}
