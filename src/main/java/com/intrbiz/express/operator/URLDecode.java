package com.intrbiz.express.operator;

import java.net.URLDecoder;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class URLDecode extends Function
{

	public URLDecode()
	{
		super("urldecode");
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
				return URLDecoder.decode(str, "UTF-8");
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
