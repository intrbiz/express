package com.intrbiz.express.operator;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class Assignment extends BinaryOperator
{

    public Assignment()
    {
        super("=");
    }

    public Assignment(Operator l, Operator r)
    {
        this();
        this.setLeft(l);
        this.setRight(r);
    }

    @Override
    public Object get(ExpressContext context, Object source) throws ExpressException
    {
        Object r = this.getRight().get(context, source);
        this.getLeft().set(context, r, source);
        return null;
    }
    
    @Override
    public boolean isIdempotent()
    {
        return true;
    }
}
