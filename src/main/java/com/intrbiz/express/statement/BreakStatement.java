package com.intrbiz.express.statement;

import com.intrbiz.express.ELContext;
import com.intrbiz.express.ELException;

public class BreakStatement extends Statement
{

    public BreakStatement()
    {
        super();
    }

    @Override
    public void execute(ELContext ctx, Object source) throws ELException
    {
        ctx.getFrame().doBreak();
    }

    public String toString(String p)
    {
        return p  + "break;";
    }
}
