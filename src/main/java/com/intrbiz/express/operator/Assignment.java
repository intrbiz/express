package com.intrbiz.express.operator;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

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
    public Object get(ELContext context, Object source) throws ELException
    {
        Object r = this.getRight().get(context, source);
        this.getLeft().set(context, r, source);
        return null;
    }
}
