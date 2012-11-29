package com.intrbiz.express.operator;

import java.net.URLEncoder;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class URLEncode extends Function
{

	public URLEncode()
	{
		super("urlencode");
	}

	@Override
	public Object get(ELContext context, Object source) throws ELException
	{
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
	public void set(ELContext context, Object value, Object source) throws ELException
	{
	}

}
