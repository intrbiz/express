package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class BreakStatement extends Statement
{

    public BreakStatement()
    {
        super();
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        ctx.getFrame().doBreak();
    }

    public String toString(String p)
    {
        return p  + "break;";
    }
}
