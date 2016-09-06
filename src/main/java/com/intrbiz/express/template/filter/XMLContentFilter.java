package com.intrbiz.express.template.filter;

import java.io.IOException;
import java.io.Writer;

import com.intrbiz.Util;
import com.intrbiz.express.ExpressException;

public class XMLContentFilter implements ContentFilter
{
    @Override
    public String getContentType()
    {
        return "application/xml";
    }
    
    @Override
    public void filter(String text, Writer to) throws IOException
    {
        Util.xmlEncode(text, to);
    }

    @Override
    public String prefilter(String text) throws ExpressException
    {
        return Util.xmlEncode(text);
    }
}
