package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.template.io.TemplateWriter;

public class Print extends Function
{

	public Print()
	{
		super("print");
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
	    context.checkOp();
	    
		Operator op = this.getParameters().get(0);
		Object val = op.get(context, source);
		TemplateWriter writer = context.getWriter();
        if (writer != null) writer.write(String.valueOf(val));
		return val;
	}
	
	@Override
    public boolean isIdempotent()
    {
        return true;
    }
}
