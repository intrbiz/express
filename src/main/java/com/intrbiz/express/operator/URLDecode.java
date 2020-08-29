package com.intrbiz.express.operator;

import java.net.URLDecoder;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class URLDecode extends Function
{
	public URLDecode()
	{
		super("urldecode");
	}

	@Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
	    context.checkOp();
	    
		Operator op = this.getParameters().get(0);
		Object val = op.get(context, source);
		if (val != null)
		{
			String str = String.valueOf(val);
			try
			{
				return URLDecoder.decode(str, "UTF-8");
			}
			catch (Exception e)
			{
			}
		}
		return null;
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
	}

	@Override
    public boolean isIdempotent()
    {
        return true;
    }
}
