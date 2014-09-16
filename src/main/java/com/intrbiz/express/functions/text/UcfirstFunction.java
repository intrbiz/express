package com.intrbiz.express.functions.text;

import com.intrbiz.Util;
import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Function;

public class UcfirstFunction extends Function
{
    public UcfirstFunction()
    {
        super("ucfirst");
    }

    @Override
    public boolean isIdempotent()
    {
        return true;
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        if (this.getParameters().size() > 0)
        {
            Object val = this.getParameter(0).get(context, source);
            return val == null ? null : Util.ucFirst(String.valueOf(val));
        }
        return null;
    }
}
