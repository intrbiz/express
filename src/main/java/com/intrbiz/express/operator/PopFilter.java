package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.io.TemplateWriter;

/**
 * Pop the current content filter from the writer filter stack
 */
public class PopFilter extends Function
{

    public PopFilter()
    {
        super("popfilter");
    }

	@Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
	    context.checkOp();
	    
	    TemplateWriter writer = context.getWriter();
	    if (writer != null) writer.popContentFilter();
        return null;
    }
    
    @Override
    public boolean isIdempotent()
    {
        return false;
    }
}
