package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class InstanceOf extends Function
{

    public InstanceOf()
    {
        super("instanceof");
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        context.checkOp();
        
        // the operators
        Operator obj = this.getParameters().get(0);
        Operator cn = this.getParameters().get(1);
        // get the object
        Object objIns = obj.get(context, source);
        Object cnIns = cn.get(context, source);
        // check
        if (objIns == null)
            return false;
        // load the class
        try
        {
            Class<?> cls = Class.forName((String) cnIns);
            if (cls == null)
                return false;
            return cls.isInstance(objIns);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
