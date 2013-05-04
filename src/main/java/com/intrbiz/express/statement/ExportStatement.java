package com.intrbiz.express.statement;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class ExportStatement extends Statement
{

    private final String variable;

    public ExportStatement(String variable)
    {
        super();
        this.variable = variable;
    }

    public String getVariable()
    {
        return variable;
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        String var = this.getVariable();
        Object value = ctx.getEntity(var, source);
        ctx.exportEntity(var, value, source);
    }

    public String toString(String p)
    {
        return p + "export " + this.getVariable() + ";";
    }
}
