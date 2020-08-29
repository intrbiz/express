package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class ContinueStatement extends Statement
{

    public ContinueStatement()
    {
        super();
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        ctx.getFrame().doContinue();
    }

    public String toString(String p)
    {
        return p  + "continue;\r\n";
    }
}
