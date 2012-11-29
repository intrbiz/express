package com.intrbiz.express.statement;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;
import com.intrbiz.express.operator.Operator;

public class ReturnStatement extends DefaultStatement
{

    public ReturnStatement()
    {
        super();
    }

    public ReturnStatement(Operator operator) throws ELException
    {
        super(operator);
    }

    @Override
    public void execute(ELContext ctx, Object source) throws ELException
    {
        Object ret = this.getOperator().get(ctx, source);
        ctx.getFrame().doReturn(ret);
    }

    public String toString(String p)
    {
        return p  + "return " + this.getOperator().toString() + ";";
    }
}
