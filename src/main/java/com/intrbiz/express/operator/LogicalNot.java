package com.intrbiz.express.operator;

import java.util.Collection;
import java.util.Map;

import com.intrbiz.Util;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class LogicalNot extends UnaryOperator
{

	public LogicalNot()
	{
		super("!");
	}

	public LogicalNot(Operator o)
	{
		this();
		this.setOperator(o);
	}

	@SuppressWarnings("rawtypes")
    @Override
	public Object get(ExpressContext context, Object source) throws ExpressException
	{
	    context.checkOp();
	    
		Operator val = this.getOperator();
		Object eval = val.get(context, source);
		if (eval == null)
		{
		    return true;
		}
		else if (eval instanceof Boolean)
		{
			boolean beval = ((Boolean) eval).booleanValue();
			return new Boolean(!beval);
		}
		else if (eval instanceof String)
        {
            return Util.isEmpty((String) eval);
        }
        else if (eval instanceof Collection)
        {
            return Util.isEmpty((Collection) eval);
        }
        else if (eval instanceof Map)
        {
            return Util.isEmpty((Map) eval);
        }
        else if (eval instanceof Object[])
        {
            return Util.isEmpty((Object[]) eval);
        }
		return false;
	}

	@Override
	public void set(ExpressContext context, Object value, Object source) throws ExpressException
	{
	}
}
