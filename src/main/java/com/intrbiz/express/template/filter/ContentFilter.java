package com.intrbiz.express.template.filter;

import java.io.IOException;
import java.io.Writer;

import com.intrbiz.express.ExpressException;

public interface ContentFilter
{
    /**
     * The content type this filter deals with
     */
    String getContentType();
    
    /**
     * Filter the given text to the given writer
     * @param text the text to filter
     * @param to where to write the filtered text too
     * @throws IOException on an underlying IO issue
     * @throws ExpressException for anything else
     */
    void filter(String text, Writer to) throws IOException, ExpressException;
    
    /**
     * Precompute the filtered text from the given text
     * @param text the text to filter
     * @return the filtered result
     * @throws ExpressException for anytying that goes wrong
     */
    String prefilter(String text) throws ExpressException;
}
