package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.io.TemplateWriter;

/**
 * Push a content filter for the given content type onto the writer filter stack
 */
public class PushFilter extends Function
{

    public PushFilter()
    {
        super("pushfilter");
    }

	@Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
	    context.checkOp();
	    
	    TemplateWriter writer = context.getWriter();
	    if (writer != null)
	    {
    	    Object contentType = this.getParameter(0).get(context, source);
    	    if (contentType instanceof String)
    	    {
    	        writer.pushContentFilter((String) contentType); 
    	    }
    	    else
    	    {
    	        throw new ExpressException("Content type must be a String");
    	    }
	    }
        return null;
    }
    
    @Override
    public boolean isIdempotent()
    {
        return false;
    }
}
