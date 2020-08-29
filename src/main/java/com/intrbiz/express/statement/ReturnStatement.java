package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;
import com.intrbiz.express.operator.Operator;

public class ReturnStatement extends DefaultStatement
{

    public ReturnStatement()
    {
        super();
    }

    public ReturnStatement(Operator operator) throws ExpressException
    {
        super(operator);
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        Operator operator = this.getOperator();
        ctx.getFrame().doReturn(operator == null ? null : operator.get(ctx, source));
    }

    public String toString(String p)
    {
        return p  + "return " + (this.getOperator() == null ? "" : this.getOperator().toString()) + ";\r\n";
    }
}
