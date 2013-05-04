package com.intrbiz.express.statement;

import java.util.LinkedList;
import java.util.List;

import com.intrbiz.express.ExpressContext;
import com.intrbiz.express.ExpressException;

public class StatementBlock extends Statement
{
    private List<Statement> statements = new LinkedList<Statement>();

    public StatementBlock()
    {
        super();
    }

    public StatementBlock(List<Statement> statements)
    {
        this.statements = statements;
    }

    @Override
    public void execute(ExpressContext ctx, Object source) throws ExpressException
    {
        try
        {
            ctx.enterFrame(false);
            for (Statement stmt : this.getStatements())
            {
                stmt.execute(ctx, source);
                // check the context state
                if (ctx.getFrame().isHalt()) return;
            }
        }
        finally
        {
            ctx.exitFrame();
        }
    }

    public List<Statement> getStatements()
    {
        return statements;
    }

    public void setStatements(List<Statement> statements)
    {
        this.statements = statements;
    }

    public String toString(String p)
    {
        StringBuilder sb = new StringBuilder();
        for (Statement s : this.getStatements())
        {
            sb.append(s.toString(p)).append("\r\n");
        }
        return sb.toString();
    }
}
