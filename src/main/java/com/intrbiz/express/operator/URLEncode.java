package com.intrbiz.express.operator;

import java.net.URLEncoder;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class URLEncode extends Function
{
	public URLEncode()
	{
		super("urlencode");
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
				return URLEncoder.encode(str, "UTF-8");
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
