package com.intrbiz.express.functions.text;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Function;

public class JoinFunction extends Function
{
    public JoinFunction()
    {
        super("join");
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
            String sep = String.valueOf(this.getParameter(0).get(context, source));
            boolean nc = false;
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < this.getParameters().size(); i ++)
            {
                Object val = this.getParameter(i).get(context, source);
                if (val instanceof Iterable)
                {
                    for (Object e :  (Iterable<?>) val)
                    {
                        if (nc) sb.append(sep);
                        sb.append(e);
                        nc = true;
                    }
                }
                else if (val != null)
                {
                    if (nc) sb.append(sep);
                    sb.append(val);
                    nc = true;
                }
            }
            return sb.toString();
        }
        return "";
    }
}
